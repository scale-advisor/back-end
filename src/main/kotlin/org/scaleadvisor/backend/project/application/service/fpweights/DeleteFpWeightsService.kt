package org.scaleadvisor.backend.project.application.service.fpweights

import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.fpweights.DeleteFpWeightsPort
import org.scaleadvisor.backend.project.application.port.usecase.fpweights.DeleteFpWeightsUseCase
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsEditorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.GetProjectUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteFpWeightsService(
    private val getProjectUseCase: GetProjectUseCase,
    private val checkIsEditorUseCase: CheckIsEditorUseCase,
    private val deleteFpWeightsPort: DeleteFpWeightsPort
): DeleteFpWeightsUseCase {

    override fun delete(projectId: ProjectId) {

        if (!checkIsEditorUseCase.checkIsEditor(projectId.toLong())) {
            throw ForbiddenException("프로젝트 요소를 수정할 권한이 없습니다.")
        }

        getProjectUseCase.find(projectId)
            ?: throw NotFoundException("프로젝트를 찾을 수 없습니다. (id=${projectId.value})")

        deleteFpWeightsPort.delete(projectId)
    }

}