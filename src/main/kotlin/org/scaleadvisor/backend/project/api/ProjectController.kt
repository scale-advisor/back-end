package org.scaleadvisor.backend.project.api

import org.scaleadvisor.backend.api.ProjectAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.api.request.CreateProjectRequest
import org.scaleadvisor.backend.project.api.response.CreateProjectResponse
import org.scaleadvisor.backend.project.api.response.FindAllProjectResponse
import org.scaleadvisor.backend.project.application.port.usecase.CreateProjectUseCase
import org.scaleadvisor.backend.project.domain.Project
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectController(
    private val createProjectUseCase: CreateProjectUseCase
) : ProjectAPI {
    override fun create(request: CreateProjectRequest): SuccessResponse<CreateProjectResponse> {
        val project: Project = createProjectUseCase.create(
            CreateProjectUseCase.CreateProjectCommand(
                request.name,
                request.description
            )
        )

        return SuccessResponse.from(
            CreateProjectResponse.from(project)
        )
    }

    override fun findAll(): SuccessResponse<FindAllProjectResponse> {
        TODO("Not yet implemented")
//        val currentUserId = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
//            ?: throw ForbiddenException("현재 인증이 되지 않은 접근 입니다.")

    }
}