package org.scaleadvisor.backend.project.application.port.usecase.requirement

import org.scaleadvisor.backend.project.domain.id.RequirementId

interface DeleteRequirementUseCase {

    fun deleteAll(requirementIdList: List<RequirementId>)

}