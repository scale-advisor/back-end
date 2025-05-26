package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.ProjectRequirementAPI
import org.scaleadvisor.backend.project.application.port.usecase.requirement.CreateRequirementUseCase
import org.scaleadvisor.backend.project.controller.request.requirement.CreateRequirementRequest
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectRequirementController(
    private val createRequirementUseCase: CreateRequirementUseCase,
) : ProjectRequirementAPI {

    override fun create(
        projectId: Long,
        requirementList: List<CreateRequirementRequest>
    ) {
        createRequirementUseCase.createAll(
            projectId = ProjectId.from(projectId),
            requirementDTOList = requirementList.map {
                CreateRequirementUseCase.RequirementDTO(
                    number = it.requirementNumber,
                    name = it.requirementName,
                    detailNumber = it.requirementDetailNumber,
                    detail = it.requirementDetail,
                    type = it.requirementType,
                    note = it.note,
                )
            },
        )
    }

}