package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.ProjectRequirementAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.application.port.usecase.requirement.CreateRequirementCategoryUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirement.CreateRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirement.GetRequirementUseCase
import org.scaleadvisor.backend.project.controller.request.requirement.CreateRequirementCategoryRequest
import org.scaleadvisor.backend.project.controller.request.requirement.CreateRequirementRequest
import org.scaleadvisor.backend.project.controller.response.requirement.GetAllRequirementResponse
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectRequirementController(
    private val createRequirementUseCase: CreateRequirementUseCase,
    private val getRequirementUseCase: GetRequirementUseCase,
    private val createRequirementCategoryUseCase: CreateRequirementCategoryUseCase,
) : ProjectRequirementAPI {

    override fun createAll(
        projectId: Long,
        requirementList: List<CreateRequirementRequest>
    ) {
        createRequirementUseCase.createAll(
            projectId = ProjectId.from(projectId),
            requirementDTOList = requirementList.map {
                CreateRequirementUseCase.RequirementDTO(
                    number = it.requirementNumber,
                    name = it.requirementName,
                    definition = it.requirementDefinition,
                    detail = it.requirementDetail,
                    type = it.requirementType,
                )
            },
        )
    }

    override fun findAll(
        projectId: Long,
        versionNumber: String
    ): SuccessResponse<GetAllRequirementResponse> {
        val projectVersion = ProjectVersion.of(projectId, versionNumber)
        val requirementList = getRequirementUseCase.findAll(projectVersion)
        return SuccessResponse.from(
            GetAllRequirementResponse.from(requirementList)
        )
    }

    override fun createCategory(
        projectId: Long,
        requirementCategoryList: List<CreateRequirementCategoryRequest>
    ) {
        createRequirementCategoryUseCase.createAll(
            projectId = ProjectId.from(projectId),
            requirementCategoryDTOList = requirementCategoryList.map { requirementCategory ->
                CreateRequirementCategoryUseCase.RequirementCategoryDTO(
                    name = requirementCategory.requirementCategoryName,
                    prefix = requirementCategory.requirementCategoryPrefix,
                )
            },
        )
    }

}