package org.scaleadvisor.backend.project.controller.request.requirement

data class CreateRequirementRequest (
    val requirementNumber: String,
    val requirementName: String,
    val requirementDefinition: String,
    val requirementDetailNumber: String,
    val requirementDetail: String,
    val requirementType: String,
    val note: String,
)