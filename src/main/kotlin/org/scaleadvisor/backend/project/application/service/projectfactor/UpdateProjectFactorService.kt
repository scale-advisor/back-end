package org.scaleadvisor.backend.project.application.service.projectfactor

import org.scaleadvisor.backend.project.application.port.repository.projectfactor.UpdateProjectFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.projectfactor.UpdateProjectFactorUseCase
import org.scaleadvisor.backend.project.domain.ProjectFactor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class UpdateProjectFactorService(
    private val updateProjectFactorPort: UpdateProjectFactorPort
) : UpdateProjectFactorUseCase {
    override fun update(projectFactor: ProjectFactor) {
        updateProjectFactorPort.update(projectFactor)
    }
}