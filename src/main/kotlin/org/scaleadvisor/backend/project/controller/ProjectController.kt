package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.ProjectAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.global.exception.model.UnauthorizedException
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.scaleadvisor.backend.project.application.port.usecase.project.CreateProjectUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.DeleteProjectUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.GetProjectUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.UpdateProjectUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.GetProjectVersionUseCase
import org.scaleadvisor.backend.project.controller.request.project.CreateProjectRequest
import org.scaleadvisor.backend.project.controller.request.project.UpdateProjectRequest
import org.scaleadvisor.backend.project.controller.response.project.CreateProjectResponse
import org.scaleadvisor.backend.project.controller.response.project.GetAllProjectResponse
import org.scaleadvisor.backend.project.controller.response.project.UpdateProjectResponse
import org.scaleadvisor.backend.project.domain.Project
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectController(
    private val createProjectUseCase: CreateProjectUseCase,
    private val getProjectUseCase: GetProjectUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val getProjectVersionUseCase: GetProjectVersionUseCase
) : ProjectAPI {
    override fun create(request: CreateProjectRequest): SuccessResponse<CreateProjectResponse> {
        val currentUserId = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw UnauthorizedException("만료 되거나 잘못된 인증 입니다.")

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

    override fun findAll(): SuccessResponse<GetAllProjectResponse> {
        val currentUserId = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw UnauthorizedException("만료 되거나 잘못된 인증 입니다.")
        val projects : List<GetAllProjectResponse.ProjectDTO> = getProjectUseCase.findAll(currentUserId)
            .map { project ->
                GetAllProjectResponse.ProjectDTO.of(
                    project = project,
                    versionList = getProjectVersionUseCase.findAll(project.id)
                )
            }

        return SuccessResponse.from(GetAllProjectResponse.from(projects))
    }

    override fun update(
        projectId: Long,
        request: UpdateProjectRequest
    ): SuccessResponse<UpdateProjectResponse> {
        val currentUserId = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw UnauthorizedException("만료 되거나 잘못된 인증 입니다.")

        val project: Project = updateProjectUseCase.update(
            UpdateProjectUseCase.UpdateProjectCommand(
                userId = currentUserId,
                projectId = ProjectId.of(projectId),
                name = request.name,
                description = request.description
            )
        )

        return SuccessResponse.from(
            UpdateProjectResponse.from(project)
        )
    }

    override fun delete(projectId: Long) {
        val currentUserId = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw UnauthorizedException("만료 되거나 잘못된 인증 입니다.")

        deleteProjectUseCase.delete(
            userId = currentUserId,
            projectId = ProjectId.of(projectId)
        )
    }
}