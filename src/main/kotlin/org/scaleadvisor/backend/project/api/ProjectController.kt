package org.scaleadvisor.backend.project.api

import org.scaleadvisor.backend.api.ProjectAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.scaleadvisor.backend.project.api.request.CreateProjectRequest
import org.scaleadvisor.backend.project.api.request.UpdateProjectRequest
import org.scaleadvisor.backend.project.api.response.CreateProjectResponse
import org.scaleadvisor.backend.project.api.response.FindAllProjectResponse
import org.scaleadvisor.backend.project.api.response.UpdateProjectResponse
import org.scaleadvisor.backend.project.application.port.usecase.*
import org.scaleadvisor.backend.project.domain.Project
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectController(
    private val createProjectUseCase: CreateProjectUseCase,
    private val getProjectUseCase: GetProjectUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val getProjectVersionListUseCase: GetProjectVersionListUseCase
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
        val projects : List<FindAllProjectResponse.ProjectDTO> = getProjectUseCase.findAll(currentUserId)
            .map { project ->
                FindAllProjectResponse.ProjectDTO(
                    id = project.id.toString(),
                    name = project.name,
                    description = project.description,
                    updatedAt = project.updatedAt!!,
                    versionList = getProjectVersionListUseCase.findAll(project.id)
                )
            }

        return SuccessResponse.from(FindAllProjectResponse.from(projects))
    }

    override fun update(
        projectId: Long,
        request: UpdateProjectRequest
    ): SuccessResponse<UpdateProjectResponse> {
        val currentUserId = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw ForbiddenException("현재 인증이 되지 않은 접근 입니다.")

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
            ?: throw ForbiddenException("현재 인증이 되지 않은 접근 입니다.")

        deleteProjectUseCase.delete(
            userId = currentUserId,
            projectId = ProjectId.of(projectId)
        )
    }
}