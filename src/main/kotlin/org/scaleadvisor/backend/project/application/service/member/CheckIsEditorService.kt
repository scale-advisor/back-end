package org.scaleadvisor.backend.project.application.service.member

import org.scaleadvisor.backend.project.application.port.repository.member.CheckIsEditorPort
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsEditorUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class CheckIsEditorService(
    private val checkIsEditorPort: CheckIsEditorPort
): CheckIsEditorUseCase {

    override fun checkIsEditor(projectId: Long, userId: Long): Boolean {
        return checkIsEditorPort.checkIsEditor(projectId, userId)
    }
}