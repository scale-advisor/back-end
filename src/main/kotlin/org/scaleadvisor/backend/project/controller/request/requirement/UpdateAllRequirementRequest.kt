package org.scaleadvisor.backend.project.controller.request.requirement

data class UpdateAllRequirementRequest (
    val requirementId: String,
    val requirementNumber: String,
    val requirementName: String,
    val requirementDefinition: String,
    val requirementDetail: String,
    val requirementType: String,
)