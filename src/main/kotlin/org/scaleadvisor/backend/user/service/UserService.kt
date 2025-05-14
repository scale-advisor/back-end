package org.scaleadvisor.backend.user.service

import org.scaleadvisor.backend.global.exception.constant.UserMessageConstant
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun deleteUser(id: Long) {
        val user = userRepository.findById(id)
            ?: throw NotFoundException(String.format(UserMessageConstant.NOT_FOUND_USER_ID_MESSAGE, id))

        if(user.userId == id){
            userRepository.deleteUser(id)
        }
    }
}