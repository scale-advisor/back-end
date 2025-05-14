package org.scaleadvisor.backend.user.service

import org.scaleadvisor.backend.global.exception.constant.UserMessageConstant
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun updateName(userId: Long, newName: String) {
        if (userRepository.updateNameById(userId, newName) != 1){
            throw NotFoundException(String.format(UserMessageConstant.NOT_FOUND_USER_ID_MESSAGE, userId))
        }
    }

    fun deleteUser(userId: Long) {
        if (userRepository.deleteUser(userId) != 1) {
            throw NotFoundException(String.format(UserMessageConstant.NOT_FOUND_USER_ID_MESSAGE, userId))
        }
    }
}