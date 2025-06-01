package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.ProjectRequirementAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.application.port.usecase.requirement.*
import org.scaleadvisor.backend.project.controller.request.requirement.CreateAllRequirementCategoryRequest
import org.scaleadvisor.backend.project.controller.request.requirement.CreateAllRequirementRequest
import org.scaleadvisor.backend.project.controller.request.requirement.UpdateAllRequirementRequest
import org.scaleadvisor.backend.project.controller.response.requirement.GetAllRequirementResponse
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectRequirementController(
    private val createRequirementUseCase: CreateRequirementUseCase,
    private val getRequirementUseCase: GetRequirementUseCase,
    private val updateRequirementUseCase: UpdateRequirementUseCase,
    private val deleteRequirementUseCase: DeleteRequirementUseCase,
    private val createRequirementCategoryUseCase: CreateRequirementCategoryUseCase,
) : ProjectRequirementAPI {

    override fun createAll(
        projectId: Long,
        versionNumber: String,
        requirementList: List<CreateAllRequirementRequest>
    ) {
        val projectVersion = ProjectVersion.of(projectId, versionNumber)
        createRequirementUseCase.createAll(
            projectVersion,
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

    override fun updateAll(
        requirementList: List<UpdateAllRequirementRequest>
    ) {
        updateRequirementUseCase.updateAll(
            requirementList.map {
                UpdateRequirementUseCase.RequirementDTO(
                    id = RequirementId.from(it.requirementId),
                    number = it.requirementNumber,
                    name = it.requirementName,
                    definition = it.requirementDefinition,
                    detail = it.requirementDetail,
                    type = it.requirementType,
                )
            }
        )

    }

    override fun deleteAll(
        requirementIdList: List<String>
    ) {
        deleteRequirementUseCase.deleteAll(requirementIdList.map { RequirementId.from(it) })
    }

    override fun createAllCategory(
        projectId: Long,
        requirementCategoryList: List<CreateAllRequirementCategoryRequest>
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