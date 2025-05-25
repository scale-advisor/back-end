package org.scaleadvisor.backend.project.application.service.member

import org.scaleadvisor.backend.global.exception.model.UnauthorizedException
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.scaleadvisor.backend.project.application.port.repository.member.CheckIsEditorPort
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsEditorUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class CheckIsEditorService(
    private val checkIsEditorPort: CheckIsEditorPort
): CheckIsEditorUseCase {

    override fun checkIsEditor(projectId: Long): Boolean {
        val currentUserId = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw UnauthorizedException("만료 되거나 잘못된 인증 입니다.")

        return checkIsEditorPort.checkIsEditor(projectId, currentUserId)
    }
}