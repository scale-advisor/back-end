package org.scaleadvisor.backend.project.controller.request.requirement

data class CreateAllRequirementRequest (
    val requirementNumber: String,
    val requirementName: String,
    val requirementDefinition: String,
    val requirementDetail: String,
    val requirementType: String,
)