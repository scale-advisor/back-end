package org.scaleadvisor.backend.project.api

import org.scaleadvisor.backend.api.ProjectAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.scaleadvisor.backend.project.api.request.CreateProjectRequest
import org.scaleadvisor.backend.project.api.response.CreateProjectResponse
import org.scaleadvisor.backend.project.api.response.FindAllProjectResponse
import org.scaleadvisor.backend.project.application.port.usecase.CreateProjectUseCase
import org.scaleadvisor.backend.project.application.port.usecase.GetProjectUseCase
import org.scaleadvisor.backend.project.domain.Project
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectController(
    private val createProjectUseCase: CreateProjectUseCase,
    private val getProjectUseCase: GetProjectUseCase
) : ProjectAPI {
    override fun create(request: CreateProjectRequest): SuccessResponse<CreateProjectResponse> {
        val currentUserId = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw ForbiddenException("현재 인증이 되지 않은 접근 입니다.")

        val project: Project = createProjectUseCase.create(
            CreateProjectUseCase.CreateProjectCommand(
                userId = currentUserId,
                name = request.name,
                description = request.description
            )
        )

        return SuccessResponse.from(
            CreateProjectResponse.from(project)
        )
    }

    override fun findAll(): SuccessResponse<FindAllProjectResponse> {
        val currentUserId = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw ForbiddenException("현재 인증이 되지 않은 접근 입니다.")
        val projects : List<Project> = getProjectUseCase.findAll(currentUserId)

        return SuccessResponse.from(FindAllProjectResponse.from(projects))
    }
}