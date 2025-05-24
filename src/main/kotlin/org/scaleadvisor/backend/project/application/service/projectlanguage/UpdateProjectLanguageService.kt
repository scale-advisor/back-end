package org.scaleadvisor.backend.project.application.service.projectlanguage

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
    private val deleteProjectLanguageUseCase: DeleteProjectLanguageUseCase,
    private val createProjectLanguageUseCase: CreateProjectLanguageUseCase
) : UpdateProjectLanguageUseCase {
    override fun updateAll(
        projectId: ProjectId,
        projectLanguageList: List<ProjectLanguage>
    ) {
        deleteProjectLanguageUseCase.deleteAll(projectId)
        createProjectLanguageUseCase.createAll(projectId, projectLanguageList)
    }
}