package org.scaleadvisor.backend.project.application.service.projectlanguage

import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.global.exception.model.UnauthorizedException
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsEditorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectlanguage.CreateProjectLanguageUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectlanguage.DeleteProjectLanguageUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectlanguage.UpdateProjectLanguageUseCase
import org.scaleadvisor.backend.project.domain.ProjectLanguage
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class UpdateProjectLanguageService(
    private val checkIsEditorUseCase: CheckIsEditorUseCase,
    private val deleteProjectLanguageUseCase: DeleteProjectLanguageUseCase,
    private val createProjectLanguageUseCase: CreateProjectLanguageUseCase
) : UpdateProjectLanguageUseCase {
    override fun updateAll(
        projectId: ProjectId,
        projectLanguageList: List<ProjectLanguage>
    ) {

        if (!checkIsEditorUseCase.checkIsEditor(
                projectId.toLong())) {
            throw ForbiddenException("프로젝트 요소를 수정할 권한이 없습니다.")
        }

        deleteProjectLanguageUseCase.deleteAll(projectId)
        createProjectLanguageUseCase.createAll(projectId, projectLanguageList)
    }
}