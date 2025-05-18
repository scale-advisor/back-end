package org.scaleadvisor.backend.project.api

import org.scaleadvisor.backend.project.api.request.CreateProjectRequest
import org.scaleadvisor.backend.project.api.response.CreateProjectResponse
import org.scaleadvisor.backend.project.application.port.usecase.CreateProjectUseCase
import org.scaleadvisor.backend.project.domain.Project
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectController(
    private val createProjectUseCase: CreateProjectUseCase
) : ProjectAPI {
    override fun create(request: CreateProjectRequest): CreateProjectResponse {
        val project: Project = createProjectUseCase.execute(
            CreateProjectUseCase.CreateProjectCommand(
                request.name,
                request.description
            )
        )

        return CreateProjectResponse.from(project)
    }
}