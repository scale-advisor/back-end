package org.scaleadvisor.backend.user.service

import org.scaleadvisor.backend.global.exception.constant.UserMessageConstant
import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.scaleadvisor.backend.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    private val currentUserId: Long
        get() = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw ForbiddenException("현재 인증이 되지 않은 접근 입니다.")

    fun updateName(newName: String) {
        if (userRepository.updateNameById(currentUserId, newName) != 1){
            throw NotFoundException(String.format(UserMessageConstant.NOT_FOUND_USER_ID_MESSAGE, currentUserId))
        }
    }

    fun deleteUser() {
        if (userRepository.deleteUser(currentUserId) != 1) {
            throw NotFoundException(String.format(UserMessageConstant.NOT_FOUND_USER_ID_MESSAGE, currentUserId))
        }
    }
}