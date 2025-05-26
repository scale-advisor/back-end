package org.scaleadvisor.backend.project.application.port.usecase.requirement

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface CreateRequirementUseCase {

    data class RequirementDTO(
        val number: String,
        val name: String,
        val detailNumber: String,
        val detail: String,
        val type: String,
        val note: String,
    )

    fun createAll(projectId: ProjectId, requirementDTOList: List<RequirementDTO>)

}