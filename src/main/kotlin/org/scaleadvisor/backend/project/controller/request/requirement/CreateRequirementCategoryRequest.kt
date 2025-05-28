package org.scaleadvisor.backend.project.controller.request.requirement

import org.scaleadvisor.backend.project.domain.enum.RequirementCategoryName

data class CreateRequirementCategoryRequest (
    val requirementCategoryName: RequirementCategoryName,
    val requirementCategoryPrefix: String
)