package org.scaleadvisor.backend.project.application.service.projectfactor

import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.global.exception.model.UnauthorizedException
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.scaleadvisor.backend.project.application.port.repository.projectfactor.UpdateProjectFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsEditorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectfactor.UpdateProjectFactorUseCase
import org.scaleadvisor.backend.project.domain.ProjectFactor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class UpdateProjectFactorService(
    private val checkIsEditorUseCase: CheckIsEditorUseCase,
    private val updateProjectFactorPort: UpdateProjectFactorPort
) : UpdateProjectFactorUseCase {
    override fun update(projectFactor: ProjectFactor) {

        if (!checkIsEditorUseCase.checkIsEditor(
                projectFactor.projectId.toLong())) {
            throw ForbiddenException("프로젝트 요소를 추가할 권한이 없습니다.")
        }

        updateProjectFactorPort.update(projectFactor)
    }
}