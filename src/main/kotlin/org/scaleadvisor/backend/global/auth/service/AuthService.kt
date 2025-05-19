package org.scaleadvisor.backend.global.auth.service

import jakarta.servlet.http.HttpServletResponse
import org.scaleadvisor.backend.global.config.SecurityConfig
import org.scaleadvisor.backend.global.exception.constant.UserMessageConstant
import org.scaleadvisor.backend.global.jwt.JwtProvider
import org.scaleadvisor.backend.global.oauth.kakao.component.KakaoCallbackService
import org.scaleadvisor.backend.global.oauth.kakao.dto.KakaoCallbackRequest
import org.scaleadvisor.backend.global.util.IdUtil
import org.scaleadvisor.backend.user.domain.User
import org.scaleadvisor.backend.global.auth.dto.LoginRequest
import org.scaleadvisor.backend.global.auth.dto.LoginResponse
import org.scaleadvisor.backend.global.auth.dto.SignUpRequest
import org.scaleadvisor.backend.global.exception.model.*
import org.scaleadvisor.backend.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit

@Service
@Transactional
class AuthService(
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val securityConfig: SecurityConfig,
    private val redisTemplate: RedisTemplate<String, String>,
    private val kakaoCallbackService: KakaoCallbackService
) {
    private val REFRESH_TOKEN_PREFIX = "RT:"
    private val SOCIAL_TOKEN_PREFIX = "ST:"

    @Value("\${cookies.domain}")
    private lateinit var cookieDomain: String

    fun signup(request: SignUpRequest): String {
        if (userRepository.existsByEmail(request.email)) {
            throw ConflictException(String.format(UserMessageConstant.DUPLICATE_EMAIL_MESSAGE, request.email))
        }

        val generatedId = IdUtil.generateId()
        val encodedPassword = securityConfig.passwordEncoder().encode(request.password)

        val newUser = User.of(
            email = request.email,
            password = encodedPassword,
            name = request.name,
            loginType = User.LoginType.BASIC
        )

        return userRepository.createUser(newUser, generatedId)
    }

    fun login(request: LoginRequest, response: HttpServletResponse, socialToken: String? = null
    ): LoginResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw NotFoundException(String.format(UserMessageConstant.NOT_FOUND_EMAIL_MESSAGE, request.email))

        if (!securityConfig.passwordEncoder().matches(request.password, user.password)) {
            throw ValidationException(UserMessageConstant.INVALID_CREDENTIALS_MESSAGE)
        }

        val accessToken = jwtProvider.createAccessToken(user.email, user.name)
        val refreshToken = jwtProvider.createRefreshToken(user.email)

        val redisKey = "$REFRESH_TOKEN_PREFIX${user.email}"
        redisTemplate.opsForValue()
            .set(redisKey, refreshToken, jwtProvider.REFRESH_TOKEN_VALID_MILLISECOND.toLong(), TimeUnit.MILLISECONDS)

        if (user.loginType == User.LoginType.KAKAO && socialToken != null) {
            val externalKey = "$SOCIAL_TOKEN_PREFIX${user.email}"
            redisTemplate.opsForValue()
                .set(externalKey, socialToken, jwtProvider.REFRESH_TOKEN_VALID_MILLISECOND.toLong(), TimeUnit.MILLISECONDS)
        }

        val maxAgeSec = (jwtProvider.REFRESH_TOKEN_VALID_MILLISECOND.toLong() / 1000L).toInt()
        response.setHeader(
            "Set-Cookie",
            "refreshToken=$refreshToken; HttpOnly; Secure; SameSite=None; Domain=${cookieDomain}; Path=/; Max-Age=$maxAgeSec"
        )

        return LoginResponse(accessToken = accessToken)
    }

    fun kakaoLogin(request: KakaoCallbackRequest, response: HttpServletResponse
    ): LoginResponse {
        val kakaoAccessToken = kakaoCallbackService.getKakaoToken(request.code)
        val userInfo = kakaoCallbackService.getUserInfoFromKakaoToken(kakaoAccessToken)

        val kakaoUserId = userInfo["id"].toString()
        val nickname = userInfo["nickname"].toString()
        val email = userInfo["email"].toString()

        if (!userRepository.existsByEmail(email)) {
            val generatedId = IdUtil.generateId()
            val encodedPassword = securityConfig.passwordEncoder().encode(kakaoUserId)
            val newUser = User.of(
                email = email,
                password = encodedPassword,
                name = nickname,
                loginType = User.LoginType.KAKAO,
                socialId = kakaoUserId,
                confirmed = User.Confirmed.Y
            )
            userRepository.createUser(newUser, generatedId)
        }

        return login(
            LoginRequest(email = email, password = kakaoUserId),
            response,
            socialToken = kakaoAccessToken
        )
    }

    fun logout(refreshToken: String?, response: HttpServletResponse
    ) {
        val claims = jwtProvider.parseClaims(refreshToken!!)
        val email = claims["email"].toString()
        val user = userRepository.findByEmail(email)
            ?:throw NotFoundException(String.format(UserMessageConstant.NOT_FOUND_USER_ID_MESSAGE))
        val expMillis = claims.expiration.time - System.currentTimeMillis()
        if (expMillis <= 0) return

        // 리프레시 토큰 블랙라스트화
        redisTemplate.opsForSet().add("BL:RT", refreshToken)
        redisTemplate.expire("BL:RT", expMillis, TimeUnit.MILLISECONDS)

        // 카카오 계정 로그아웃일 경우 카카오 토큰 Expire까지 시키는 식
        if (user.loginType == User.LoginType.KAKAO) {
            val externalKey = "$SOCIAL_TOKEN_PREFIX${user.email}"
            val kakaoToken = redisTemplate.opsForValue().get(externalKey)
            if (kakaoToken != null) {
                kakaoCallbackService.expireKakaoToken(kakaoToken)
                redisTemplate.delete(externalKey)
            }
        }

        redisTemplate.delete("$REFRESH_TOKEN_PREFIX$email")

        response.setHeader(
            "Set-Cookie",
            "refreshToken=$refreshToken; HttpOnly; Secure; SameSite=None; Domain=$cookieDomain; Path=/; Max-Age=0"
        )
    }

    fun refreshToken(refreshToken: String, response: HttpServletResponse
    ): LoginResponse {
        if (redisTemplate.opsForSet().isMember("BL:RT", refreshToken) == true) {
            throw UnauthorizedException("이미 로그아웃된 유저 입니다. 재로그인이 필요합니다.")
        }

        val claims = jwtProvider.parseClaims(refreshToken)
        val email = claims["email"]?.toString()
            ?: throw InvalidTokenException("토큰에 이메일 정보가 없습니다.")
        val redisKey = "$REFRESH_TOKEN_PREFIX$email"
        val savedToken = redisTemplate.opsForValue().get(redisKey)
            ?: throw UnauthorizedException("RefreshToken이 만료되었습니다. 재로그인이 필요합니다.")
        if (savedToken != refreshToken) throw InvalidTokenException("유효하지 않은 RefreshToken입니다.")

        val user = userRepository.findByEmail(email)
            ?: throw NotFoundException("존재하지 않는 사용자입니다.")
        val newAccess = jwtProvider.createAccessToken(user.email, user.name)
        val newRefresh = jwtProvider.createRefreshToken(user.email)

        redisTemplate.opsForValue()
            .set(redisKey, newRefresh, jwtProvider.REFRESH_TOKEN_VALID_MILLISECOND.toLong(), TimeUnit.MILLISECONDS)

        val maxAgeSec = (jwtProvider.REFRESH_TOKEN_VALID_MILLISECOND.toLong() / 1000L).toInt()
        response.setHeader(
            "Set-Cookie",
            "refreshToken=$refreshToken; HttpOnly; Secure; SameSite=None; Domain=$cookieDomain; Path=/; Max-Age=$maxAgeSec"
        )

        return LoginResponse(accessToken = newAccess)
    }
}