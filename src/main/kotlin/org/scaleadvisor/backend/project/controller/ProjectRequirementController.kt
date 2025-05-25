package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.ProjectRequirementAPI
import org.scaleadvisor.backend.project.application.port.usecase.requirement.CreateProjectRequirementUseCase
import org.scaleadvisor.backend.project.controller.request.requirement.CreateRequirementRequest
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectRequirementController(
    private val createProjectRequirementUseCase: CreateProjectRequirementUseCase,
) : ProjectRequirementAPI {

    override fun create(
        projectId: Long,
        requirementList: List<CreateRequirementRequest>
    ) {
        createProjectRequirementUseCase.createAll(
            projectId = ProjectId.of(projectId),
            projectRequirementList = requirementList.map { it.toDomain() }
        )
    }

}