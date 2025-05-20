package org.scaleadvisor.backend.project.api

import org.scaleadvisor.backend.api.ProjectOptionAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.api.request.CreateProjectFactorRequest
import org.scaleadvisor.backend.project.api.response.GetProjectOptionResponse
import org.scaleadvisor.backend.project.application.port.usecase.CreateProjectOptionUseCase
import org.scaleadvisor.backend.project.application.service.GetProjectOptionService
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectOptionController(
    private val createProjectOptionUseCase: CreateProjectOptionUseCase,
    private val getProjectOptionService: GetProjectOptionService

) : ProjectOptionAPI {
    override fun create(
        projectId: Long,
        request: CreateProjectFactorRequest
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

        val result = getProjectOptionService.find(projectId)

        return SuccessResponse.from(GetProjectOptionResponse.from(result))
    }
}