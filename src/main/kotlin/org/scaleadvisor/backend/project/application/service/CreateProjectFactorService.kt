package org.scaleadvisor.backend.project.application.service

import org.scaleadvisor.backend.project.application.port.repository.CreateProjectFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.CreateProjectFactorUseCase
import org.scaleadvisor.backend.project.domain.ProjectFactor
import org.scaleadvisor.backend.project.domain.id.ProjectFactorId
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
private class CreateProjectFactorService(
    private val createProjectFactorPort: CreateProjectFactorPort
) : CreateProjectFactorUseCase {
    override fun create(command: CreateProjectFactorUseCase.CreateProjectFactorCommand) {
        createProjectFactorPort.create(
            ProjectFactor(
                id = ProjectFactorId.newId(),
                projectId = command.projectId,
                unitCost = command.unitCost,
                teamSize = command.teamSize,
                cocomoType = command.cocomoType,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        )
    }
}