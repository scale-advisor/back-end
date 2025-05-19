package org.scaleadvisor.backend.project.api

import org.scaleadvisor.backend.api.ProjectOptionAPI
import org.scaleadvisor.backend.project.api.request.CreateProjectFactorRequest
import org.scaleadvisor.backend.project.application.port.usecase.CreateProjectFactorUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectOptionController(
    private val createProjectFactorUseCase: CreateProjectFactorUseCase
) : ProjectOptionAPI {
    override fun create(
        projectId: Long,
        request: CreateProjectFactorRequest
    ) {
        createProjectFactorUseCase.create(
            CreateProjectFactorUseCase.CreateProjectFactorCommand(
                projectId = ProjectId.of(projectId),
                unitCost = request.unitCost,
                teamSize = request.teamSize,
                cocomoType = request.cocomoType
            )
        )
    }
}