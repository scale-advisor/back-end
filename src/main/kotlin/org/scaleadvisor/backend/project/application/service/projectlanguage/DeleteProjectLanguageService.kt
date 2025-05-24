package org.scaleadvisor.backend.project.application.service.projectlanguage

import org.scaleadvisor.backend.project.application.port.repository.projectlanguage.DeleteProjectLanguagePort
import org.scaleadvisor.backend.project.application.port.usecase.projectlanguage.DeleteProjectLanguageUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteProjectLanguageService(
    private val deleteProjectLanguagePort: DeleteProjectLanguagePort
) : DeleteProjectLanguageUseCase {
    override fun deleteAll(projectId: ProjectId) {
        deleteProjectLanguagePort.deleteAll(projectId)
    }
}