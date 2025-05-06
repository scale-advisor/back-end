package org.scaleadvisor.backend.user.service
import org.scaleadvisor.backend.user.repository.UserRepository
import org.scaleadvisor.backend.user.dto.UserResponse
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun createUser(username: String, email: String, password: String): Long {
        return userRepository.insertUser(username, email, password)
    }

    fun getUser(id: Long): UserResponse {
        return userRepository.findUserById(id)
            ?: throw NoSuchElementException("User를 찾을 수 없습니다.")
    }
}