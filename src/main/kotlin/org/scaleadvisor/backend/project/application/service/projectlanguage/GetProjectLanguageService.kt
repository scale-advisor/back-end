package org.scaleadvisor.backend.project.application.service.projectlanguage

import org.scaleadvisor.backend.project.application.port.repository.projectlanguage.GetProjectLanguagePort
import org.scaleadvisor.backend.project.application.port.usecase.projectlanguage.GetProjectLanguageUseCase
import org.scaleadvisor.backend.project.domain.ProjectLanguage
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetProjectLanguageService(
    private val getProjectLanguagePort: GetProjectLanguagePort
) : GetProjectLanguageUseCase {
    override fun findAll(projectId: ProjectId): List<ProjectLanguage> {
        return getProjectLanguagePort.findAll(projectId)
    }
}