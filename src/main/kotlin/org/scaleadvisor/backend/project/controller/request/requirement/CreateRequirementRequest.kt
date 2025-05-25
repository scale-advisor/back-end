package org.scaleadvisor.backend.project.controller.request.requirement

import org.scaleadvisor.backend.project.domain.Requirement

data class CreateRequirementRequest (
    val requirementNumber: String,
    val requirementName: String,
    val requirementDetailNumber: String,
    val requirementDetail: String,
    val requirementType: String,
    val note: String,
) {
    fun toDomain(): Requirement = Requirement(
        number = requirementNumber,
        name = requirementName,
        detailNumber = requirementDetailNumber,
        detail = requirementDetail,
        type = requirementType,
        note = note,
    )
}