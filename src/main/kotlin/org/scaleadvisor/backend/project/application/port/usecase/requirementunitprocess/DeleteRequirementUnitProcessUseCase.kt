package org.scaleadvisor.backend.project.application.port.usecase.requirementunitprocess

import org.scaleadvisor.backend.project.domain.id.RequirementId

interface DeleteRequirementUnitProcessUseCase {
    fun deleteAll(requirementIdList: List<RequirementId>)
}