package org.scaleadvisor.backend.user.service

import org.scaleadvisor.backend.global.config.SecurityConfig
import org.scaleadvisor.backend.global.exception.constant.UserMessageConstant
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.global.exception.model.UnauthorizedException
import org.scaleadvisor.backend.global.exception.model.ValidationException
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.scaleadvisor.backend.user.dto.ChangePwdRequest
import org.scaleadvisor.backend.user.dto.DeleteUserRequest
import org.scaleadvisor.backend.user.dto.UpdateNameRequest
import org.scaleadvisor.backend.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val securityConfig: SecurityConfig
) {

    private val currentUserId: Long
        get() = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw UnauthorizedException("만료 되거나 잘못된 인증 입니다.")

    fun updateName(request: UpdateNameRequest) {
        if (userRepository.updateNameById(currentUserId, request.newName) != 1){
            throw NotFoundException(String.format(UserMessageConstant.NOT_FOUND_USER_ID_MESSAGE, currentUserId))
        }
    }

    fun updatePwd(request: ChangePwdRequest) {
        val user = userRepository.findById(currentUserId)
            ?: throw NotFoundException("사용자를 찾을 수 없습니다.")

        if (!securityConfig.passwordEncoder().matches(request.currentPwd, user.password)) {
            throw ValidationException(UserMessageConstant.INVALID_CREDENTIALS_MESSAGE)
        }

        if (request.newPwd != request.newPwdConfirm) {
            throw ValidationException(UserMessageConstant.INCORRECT_CREDENTIALS_VALID_MESSAGE)
        }

        userRepository.updatePasswordById(currentUserId,
            securityConfig.passwordEncoder().encode(request.newPwd)
        )
    }

    fun deleteUser(request: DeleteUserRequest) {
        val user = userRepository.findById(currentUserId)
            ?: throw NotFoundException(String.format(UserMessageConstant.NOT_FOUND_USER_ID_MESSAGE, currentUserId))

        if (!securityConfig.passwordEncoder().matches(request.pwdRequest, user.password)) {
            throw ValidationException(UserMessageConstant.INVALID_CREDENTIALS_MESSAGE)
        }

        userRepository.deleteUser(currentUserId)
    }
}