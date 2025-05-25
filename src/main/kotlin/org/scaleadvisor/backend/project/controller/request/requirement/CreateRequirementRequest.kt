package org.scaleadvisor.backend.project.controller.request.requirement

import org.scaleadvisor.backend.project.domain.ProjectRequirement

data class CreateRequirementRequest (
    val requirementNumber: String,
    val requirementName: String,
    val requirementDetailNumber: String,
    val requirementDetail: String,
    val requirementType: String,
    val note: String,
) {
    fun toDomain(): ProjectRequirement = ProjectRequirement(
        number = requirementNumber,
        name = requirementName,
        detailNumber = requirementDetailNumber,
        detail = requirementDetail,
        type = requirementType,
        note = note,
    )
}