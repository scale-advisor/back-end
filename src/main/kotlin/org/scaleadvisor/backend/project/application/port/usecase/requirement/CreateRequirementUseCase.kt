package org.scaleadvisor.backend.project.application.port.usecase.requirement

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.Requirement

interface CreateRequirementUseCase {

    fun createAll(requirementList: List<Requirement>)

    data class RequirementDTO(
        val number: String,
        val name: String,
        val definition: String,
        val detail: String,
        val type: String,
    )

    fun createAll(
        projectVersion: ProjectVersion,
        requirementDTOList: List<RequirementDTO>
    )

}