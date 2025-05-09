package org.scaleadvisor.backend.user.service

import org.scaleadvisor.backend.user.domain.User
import org.scaleadvisor.backend.user.dto.LoginRequest
import org.scaleadvisor.backend.user.dto.SignUpRequest
import org.scaleadvisor.backend.user.repository.UserRepository
import org.scaleadvisor.backend.user.dto.UserResponse
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository
) {

    fun signup(request: SignUpRequest): Long {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("이미 사용 중인 이메일입니다.")
        }

        val newUser = User.of(
            email = request.email,
            password = request.password,
            name = request.name,
            loginType = User.LoginType.BASIC
        )
        return userRepository.createUser(newUser)
    }

    fun login(request: LoginRequest): UserResponse {
        val user = userRepository
            .findByEmail(request.email)
            ?: throw IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.")

        return UserResponse.from(user)
    }
}