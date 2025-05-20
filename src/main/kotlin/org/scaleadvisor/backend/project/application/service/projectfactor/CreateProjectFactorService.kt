package org.scaleadvisor.backend.project.application.service.projectfactor

import org.scaleadvisor.backend.project.application.port.repository.projectfactor.CreateProjectFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.projectfactor.CreateProjectFactorUseCase
import org.scaleadvisor.backend.project.domain.ProjectFactor
import org.springframework.stereotype.Service

@Service
private class CreateProjectFactorService(
    private val createProjectFactorPort: CreateProjectFactorPort
) : CreateProjectFactorUseCase {
    override fun create(command: CreateProjectFactorUseCase.Command) {
        createProjectFactorPort.create(
            ProjectFactor(
                projectId = command.projectId,
                unitCost = command.unitCost,
                teamSize = command.teamSize,
                cocomoType = command.cocomoType,
            )
        )
    }
}