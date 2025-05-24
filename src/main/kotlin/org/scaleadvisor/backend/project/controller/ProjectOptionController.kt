package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.ProjectOptionAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.controller.request.CreateProjectOptionRequest
import org.scaleadvisor.backend.project.controller.request.UpdateProjectOptionRequest
import org.scaleadvisor.backend.project.controller.response.GetProjectOptionResponse
import org.scaleadvisor.backend.project.application.port.usecase.CreateProjectOptionUseCase
import org.scaleadvisor.backend.project.application.port.usecase.GetProjectOptionUseCase
import org.scaleadvisor.backend.project.application.port.usecase.UpdateProjectOptionUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectOptionController(
    private val createProjectOptionUseCase: CreateProjectOptionUseCase,
    private val getProjectOptionUseCase: GetProjectOptionUseCase,
    private val updateProjectOptionUseCase: UpdateProjectOptionUseCase
) : ProjectOptionAPI {
    override fun create(
        projectId: Long,
        request: CreateProjectOptionRequest
    ) {
        val projectId = ProjectId.of(projectId)
        createProjectOptionUseCase.create(
            CreateProjectOptionUseCase.Command(
                projectId = projectId,
                unitCost = request.unitCost,
                teamSize = request.teamSize,
                cocomoType = request.cocomoType,
                languageList = request.languageList
            )
        )
    }

    override fun find(projectId: Long): SuccessResponse<GetProjectOptionResponse> {
        val projectId = ProjectId.of(projectId)

        val result = getProjectOptionUseCase.find(projectId)

        return SuccessResponse.from(GetProjectOptionResponse.from(result))
    }

    override fun update(projectId: Long, request: UpdateProjectOptionRequest) {
        updateProjectOptionUseCase.update(
            UpdateProjectOptionUseCase.Command(
                projectId = ProjectId.of(projectId),
                unitCost = request.unitCost,
                teamSize = request.teamSize,
                cocomoType = request.cocomoType,
                projectLanguageList = request.projectLanguageList?.map { it.toDomain()}
            )
        )
    }
}