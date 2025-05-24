package org.scaleadvisor.backend.project.application.service.version

import org.scaleadvisor.backend.project.application.port.repository.version.DeleteVersionPort
import org.scaleadvisor.backend.project.application.port.usecase.version.DeleteVersionUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteVersionService(
    private val deleteVersionPort: DeleteVersionPort
) : DeleteVersionUseCase {
    override fun deleteAll(projectId: ProjectId) {
        deleteVersionPort.deleteAll(projectId)
    }
}