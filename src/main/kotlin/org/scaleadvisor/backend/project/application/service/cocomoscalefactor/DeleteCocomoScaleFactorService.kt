package org.scaleadvisor.backend.project.application.service.cocomoscalefactor

import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.global.exception.model.UnauthorizedException
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.scaleadvisor.backend.project.application.port.repository.cocomoscalefactor.DeleteCocomoScaleFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.cocomoscalefactor.DeleteCocomoScaleFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.cocomoscalefactor.FindCocomoScaleFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsEditorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.GetProjectUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteCocomoScaleFactorService(
    private val deleteCocomoScaleFactorPort: DeleteCocomoScaleFactorPort,
    private val getProjectUseCase: GetProjectUseCase,
    private val findCocomoScaleFactorUseCase: FindCocomoScaleFactorUseCase,
    private val checkIsEditorUseCase: CheckIsEditorUseCase
): DeleteCocomoScaleFactorUseCase {

    override fun delete(projectId: ProjectId) {

        if (!checkIsEditorUseCase.checkIsEditor(projectId.toLong())) {
            throw ForbiddenException("프로젝트 요소를 삭제할 권한이 없습니다.")
        }

        getProjectUseCase.find(projectId)
            ?: throw NotFoundException("프로젝트를 찾을 수 없습니다. (id=${projectId.value})")

        findCocomoScaleFactorUseCase.find(projectId)
            ?: throw NotFoundException("삭제할 Cocomo Scale Factor가 없습니다. (projectId=${projectId.value})")

        deleteCocomoScaleFactorPort.delete(projectId)
    }
}