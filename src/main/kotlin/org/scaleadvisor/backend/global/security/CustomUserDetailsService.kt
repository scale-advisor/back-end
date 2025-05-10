package org.scaleadvisor.backend.global.security

import org.scaleadvisor.backend.global.exception.constant.UserMessageConstant
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.user.domain.User
import org.scaleadvisor.backend.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user: User = userRepository.findByEmail(email)
            ?: throw NotFoundException(String.format(UserMessageConstant.NOT_FOUND_USER_EMAIL_MESSAGE, email))
        return CustomUserDetails(user)
    }
}