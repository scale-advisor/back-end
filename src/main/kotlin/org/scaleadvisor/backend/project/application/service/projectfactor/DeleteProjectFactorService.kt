package org.scaleadvisor.backend.project.application.service.projectfactor

import org.scaleadvisor.backend.project.application.port.repository.projectfactor.DeleteProjectFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.projectfactor.DeleteProjectFactorUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteProjectFactorService(
    private val deleteProjectFactorPort: DeleteProjectFactorPort
) : DeleteProjectFactorUseCase {
    override fun delete(projectId: ProjectId) {
        deleteProjectFactorPort.delete(projectId)
    }
}