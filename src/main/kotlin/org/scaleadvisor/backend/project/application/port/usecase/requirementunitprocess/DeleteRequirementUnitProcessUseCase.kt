package org.scaleadvisor.backend.project.application.port.usecase.requirementunitprocess

import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.scaleadvisor.backend.project.domain.id.UnitProcessId

interface DeleteRequirementUnitProcessUseCase {
    fun deleteAll(requirementIdList: List<RequirementId>)
    fun deleteAll(unitProcessIdList: List<UnitProcessId>)
}