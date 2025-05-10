package org.scaleadvisor.backend.user.service

import org.scaleadvisor.backend.global.exception.constant.UserMessageConstant
import org.scaleadvisor.backend.global.exception.model.ConflictException
import org.scaleadvisor.backend.global.exception.model.InvalidTokenException
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.global.exception.model.ValidationException
import org.scaleadvisor.backend.global.jwt.JwtProvider
import org.scaleadvisor.backend.user.domain.User
import org.scaleadvisor.backend.user.dto.LoginRequest
import org.scaleadvisor.backend.user.dto.LoginResponse
import org.scaleadvisor.backend.user.dto.SignUpRequest
import org.scaleadvisor.backend.user.repository.UserRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit

@Service
@Transactional
class AuthService(
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder,
    private val redisTemplate: RedisTemplate<String, String>
) {
    private val REFRESH_TOKEN_PREFIX = "RT:"

    fun signup(request: SignUpRequest): Long {
        if (userRepository.existsByEmail(request.email)) {
            throw ConflictException(String.format(UserMessageConstant.DUPLICATE_EMAIL_MESSAGE, request.email))
        }

        val encodedPassword = passwordEncoder.encode(request.password)

        val newUser = User.of(
            email = request.email,
            password = encodedPassword,
            name = request.name,
            loginType = User.LoginType.BASIC
        )
        return userRepository.createUser(newUser)
    }

    fun login(request: LoginRequest): LoginResponse {
        val user = userRepository
            .findByEmail(request.email)
            ?: throw NotFoundException(String.format(UserMessageConstant.NOT_FOUND_USER_EMAIL_MESSAGE, request.email))

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw ValidationException(UserMessageConstant.NOT_VALID_PASSWORD_MESSAGE)
        }
        val accessToken = jwtProvider.createAccessToken(user.userId!!, user.email)
        val refreshToken = jwtProvider.createRefreshToken(user.userId)

        val key = "$REFRESH_TOKEN_PREFIX${user.userId}"
        redisTemplate.opsForValue()
            .set(key, refreshToken, jwtProvider.REFRESH_TOKEN_VALID_MILLISECOND, TimeUnit.MILLISECONDS)

        return LoginResponse(accessToken = accessToken, refreshToken = refreshToken)
    }

    fun logout(refreshToken: String) {
        val claims = jwtProvider.parseClaims(refreshToken)
        val expMillis = claims.expiration.time - System.currentTimeMillis()
        if (expMillis <= 0) return

        // 리프레시 토큰 블랙리스트화
        val blacklistKey = "BL:RT"
        redisTemplate.opsForSet()
            .add(blacklistKey, refreshToken)
        redisTemplate.expire(blacklistKey, expMillis, TimeUnit.MILLISECONDS)
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
            .set(redisKey, newRefresh, jwtProvider.REFRESH_TOKEN_VALID_MILLISECOND, TimeUnit.MILLISECONDS)

        return LoginResponse(accessToken = newAccess, refreshToken = newRefresh)
    }
}