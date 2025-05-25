package org.scaleadvisor.backend.project.application.service.projectfactor

import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.project.application.port.repository.projectfactor.DeleteProjectFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsEditorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectfactor.DeleteProjectFactorUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteProjectFactorService(
    private val checkIsEditorUseCase: CheckIsEditorUseCase,
    private val deleteProjectFactorPort: DeleteProjectFactorPort
) : DeleteProjectFactorUseCase {
    override fun delete(projectId: ProjectId) {

        if (!checkIsEditorUseCase.checkIsEditor(
                projectId.toLong())) {
            throw ForbiddenException("프로젝트 요소를 삭제할 권한이 없습니다.")
        }

        deleteProjectFactorPort.delete(projectId)
    }
}