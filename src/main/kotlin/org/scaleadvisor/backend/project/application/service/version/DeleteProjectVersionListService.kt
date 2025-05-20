package org.scaleadvisor.backend.project.application.service.version

import org.scaleadvisor.backend.project.application.port.repository.version.DeleteVersionPort
import org.scaleadvisor.backend.project.application.port.usecase.version.DeleteProjectVersionListUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service

@Service
private class DeleteProjectVersionListService(
    private val deleteVersionPort: DeleteVersionPort
) : DeleteProjectVersionListUseCase {
    override fun deleteAll(projectId: ProjectId) {
        deleteVersionPort.deleteAll(projectId)
    }
}