package org.scaleadvisor.backend.user.service

import org.scaleadvisor.backend.global.config.SecurityConfig
import org.scaleadvisor.backend.global.email.service.EmailService
import org.scaleadvisor.backend.global.exception.constant.UserMessageConstant
import org.scaleadvisor.backend.global.exception.model.ConflictException
import org.scaleadvisor.backend.global.exception.model.InvalidTokenException
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.global.exception.model.ValidationException
import org.scaleadvisor.backend.global.jwt.JwtProvider
import org.scaleadvisor.backend.global.oauth.kakao.component.KakaoCallbackService
import org.scaleadvisor.backend.global.oauth.kakao.dto.KakaoCallbackRequest
import org.scaleadvisor.backend.global.util.IdUtil
import org.scaleadvisor.backend.user.domain.User
import org.scaleadvisor.backend.user.dto.LoginRequest
import org.scaleadvisor.backend.user.dto.LoginResponse
import org.scaleadvisor.backend.user.dto.SignUpRequest
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
    private val kakaoCallbackService: KakaoCallbackService,
    private val emailService: EmailService
) {
    private val REFRESH_TOKEN_PREFIX = "RT:"
    private val SOCIAL_TOKEN_PREFIX = "ST:"

    @Value("\${app.url}")
    private val appUrl: String = ""

    fun signup(request: SignUpRequest): Long {
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

        emailService.sendConfirmationEmail(
            request.email,
            appUrl
        )

        return userRepository.createUser(newUser, generatedId)
    }

    fun login(request: LoginRequest, socialToken: String? = null): LoginResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw NotFoundException(String.format(UserMessageConstant.NOT_FOUND_USER_EMAIL_MESSAGE, request.email))

        if (!securityConfig.passwordEncoder().matches(request.password, user.password)) {
            throw ValidationException(UserMessageConstant.INVALID_CREDENTIALS_MESSAGE)
        }
        val accessToken = jwtProvider.createAccessToken(user.userId!!, user.email)
        val refreshToken = jwtProvider.createRefreshToken(user.userId)

        val key = "$REFRESH_TOKEN_PREFIX${user.userId}"
        redisTemplate.opsForValue()
            .set(key, refreshToken, jwtProvider.REFRESH_TOKEN_VALID_MILLISECOND.toLong(), TimeUnit.MILLISECONDS)

        if (user.loginType == User.LoginType.KAKAO && socialToken != null) {
            val externalKey = "$SOCIAL_TOKEN_PREFIX${user.userId}"
            redisTemplate.opsForValue()
                .set(externalKey, socialToken, jwtProvider.REFRESH_TOKEN_VALID_MILLISECOND.toLong(), TimeUnit.MILLISECONDS)
        }

        return LoginResponse(accessToken = accessToken, refreshToken = refreshToken)
    }

    fun kakaoLogin(request: KakaoCallbackRequest): LoginResponse {
        // 테스트 시 이거 request.code로만
        val kakaoAccessToken = kakaoCallbackService.getKakaoToken(request.code)
        val userInfo = kakaoCallbackService.getUserInfoFromKakaoToken(kakaoAccessToken)

        val kakaoUserId  = userInfo["id"].toString()
        val nickname = userInfo["nickname"].toString()
        val email    = userInfo["email"].toString()

        if (!userRepository.existsByEmail(email)) {
            val generatedId = IdUtil.generateId()
            // 이 부분 같은 경우 다시 의논
            val encodedPassword = securityConfig.passwordEncoder().encode(kakaoUserId)
            val newUser = User.of(
                email     = email,
                password  = encodedPassword,
                name      = nickname,
                loginType = User.LoginType.KAKAO,
                socialId  = kakaoUserId
            )
            userRepository.createUser(newUser, generatedId)
        }

        return login(
            LoginRequest(email = email, password = kakaoUserId),
            socialToken = kakaoAccessToken
        )
    }

    fun logout(refreshToken: String) {
        val claims = jwtProvider.parseClaims(refreshToken)
        val userId = (claims["userId"] as Number).toLong()
        val user = userRepository.findById(userId) ?: return
        val expMillis = claims.expiration.time - System.currentTimeMillis()
        if (expMillis <= 0) return

        // 리프레시 토큰 블랙리스트화
        val blacklistKey = "BL:RT"
        redisTemplate.opsForSet()
            .add(blacklistKey, refreshToken)
        redisTemplate.expire(blacklistKey, expMillis, TimeUnit.MILLISECONDS)

        // 카카오 계정 로그아웃일 경우 카카오 토큰 Expire까지 시키는 식
        if (user.loginType == User.LoginType.KAKAO) {
            val externalKey = "$SOCIAL_TOKEN_PREFIX$userId"
            val kakaoToken = redisTemplate.opsForValue().get(externalKey)

            kakaoCallbackService.expireKakaoToken(kakaoToken!!)
            redisTemplate.delete(externalKey)
        }
    }

    fun refreshToken(refreshToken: String): LoginResponse {
        if (redisTemplate.opsForSet().isMember("BL:RT", refreshToken) == true) {
            throw InvalidTokenException("이미 로그아웃된 유저 입니다.")
        }

        val claims = jwtProvider.parseClaims(refreshToken)
        val userId = (claims["userId"] as Number).toLong()
        val redisKey = "$REFRESH_TOKEN_PREFIX$userId"
        val savedToken = redisTemplate.opsForValue().get(redisKey)
            ?: throw InvalidTokenException("만료되었거나 존재하지 않는 RefreshToken입니다.")

        if (savedToken != refreshToken) throw InvalidTokenException("유효하지 않은 RefreshToken입니다.")

        val user = userRepository.findById(userId)
            ?: throw NotFoundException("존재하지 않는 사용자입니다.")
        val newAccess  = jwtProvider.createAccessToken(userId, user.email)
        val newRefresh = jwtProvider.createRefreshToken(userId)

        redisTemplate.opsForValue()
            .set(redisKey, newRefresh, jwtProvider.REFRESH_TOKEN_VALID_MILLISECOND.toLong(), TimeUnit.MILLISECONDS)

        return LoginResponse(accessToken = newAccess, refreshToken = newRefresh)
    }
}