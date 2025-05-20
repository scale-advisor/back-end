package org.scaleadvisor.backend.project.application.service.projectfactor

import org.scaleadvisor.backend.project.application.port.repository.projectfactor.UpdateProjectFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.projectfactor.UpdateProjectFactorUseCase
import org.scaleadvisor.backend.project.domain.ProjectFactor
import org.springframework.stereotype.Service

@Service
private class UpdateProjectFactorService(
    private val updateProjectFactorPort: UpdateProjectFactorPort
) : UpdateProjectFactorUseCase {
    override fun update(command: UpdateProjectFactorUseCase.Command) {
        updateProjectFactorPort.update(
            ProjectFactor(
                projectId = command.projectId,
                unitCost = command.unitCost,
                teamSize = command.teamSize,
                cocomoType = command.cocomoType,
            )
        )
    }
}