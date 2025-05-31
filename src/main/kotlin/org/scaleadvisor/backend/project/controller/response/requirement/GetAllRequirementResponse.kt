package org.scaleadvisor.backend.project.controller.response.requirement

import org.scaleadvisor.backend.project.domain.Requirement

data class GetAllRequirementResponse (
    val requirementList: List<RequirementDTO>
) {
    data class RequirementDTO(
        val requirementId: String,
        val requirementNumber: String,
        val requirementName: String,
        val requirementDefinition: String,
        val requirementDetail: String,
        val requirementType: String,
    ) {
        companion object {
            @JvmStatic
            fun from(requirement: Requirement): RequirementDTO = RequirementDTO(
                requirementId = requirement.id.toString(),
                requirementNumber = requirement.number,
                requirementName = requirement.name,
                requirementDefinition = requirement.definition,
                requirementDetail = requirement.detail,
                requirementType = requirement.type,
            )
        }
    }

    companion object {
        @JvmStatic
        fun from(requirementList: List<Requirement>): GetAllRequirementResponse =
            GetAllRequirementResponse(
                requirementList.map {
                    RequirementDTO.from(it)
                }
            )
    }
}