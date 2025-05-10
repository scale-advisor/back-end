package org.scaleadvisor.backend.user.service

import org.scaleadvisor.backend.global.exception.constant.UserMessageConstant
import org.scaleadvisor.backend.global.exception.model.ConflictException
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.global.exception.model.ValidationException
import org.scaleadvisor.backend.global.jwt.JwtProvider
import org.scaleadvisor.backend.user.domain.User
import org.scaleadvisor.backend.user.dto.LoginRequest
import org.scaleadvisor.backend.user.dto.LoginResponse
import org.scaleadvisor.backend.user.dto.SignUpRequest
import org.scaleadvisor.backend.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder
) {

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
        return LoginResponse(accessToken = accessToken)
    }
}