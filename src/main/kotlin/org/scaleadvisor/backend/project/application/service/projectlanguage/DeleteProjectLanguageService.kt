package org.scaleadvisor.backend.project.application.service.projectlanguage

import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.global.exception.model.UnauthorizedException
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.scaleadvisor.backend.project.application.port.repository.projectlanguage.DeleteProjectLanguagePort
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsEditorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectlanguage.DeleteProjectLanguageUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteProjectLanguageService(
    private val checkIsEditorUseCase: CheckIsEditorUseCase,
    private val deleteProjectLanguagePort: DeleteProjectLanguagePort
) : DeleteProjectLanguageUseCase {
    override fun deleteAll(projectId: ProjectId) {

        if (!checkIsEditorUseCase.checkIsEditor(
                projectId.toLong())) {
            throw ForbiddenException("프로젝트 요소를 추가할 권한이 없습니다.")
        }

        deleteProjectLanguagePort.deleteAll(projectId)
    }
}