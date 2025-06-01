package org.scaleadvisor.backend.project.application.port.usecase.requirement

import org.scaleadvisor.backend.project.domain.id.RequirementId

fun interface UpdateRequirementUseCase {

    data class RequirementDTO(
        val id: RequirementId,
        val number: String,
        val name: String,
        val definition: String,
        val detail: String,
        val type: String,
    )

    fun updateAll(requirementDTOList: List<RequirementDTO>)

}