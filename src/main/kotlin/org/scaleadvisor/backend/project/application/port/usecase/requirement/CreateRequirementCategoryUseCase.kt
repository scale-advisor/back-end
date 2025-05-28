package org.scaleadvisor.backend.project.application.port.usecase.requirement

import org.scaleadvisor.backend.project.domain.enum.RequirementCategoryName
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface CreateRequirementCategoryUseCase {

    data class RequirementCategoryDTO(
        val name: RequirementCategoryName,
        val prefix: String,
    )

    fun createAll(projectId: ProjectId, requirementCategoryDTOList: List<RequirementCategoryDTO>)

}