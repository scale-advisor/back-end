package org.scaleadvisor.backend.project.application.port.usecase.requirementunitprocess

import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.scaleadvisor.backend.project.domain.id.UnitProcessId

interface GetRequirementUnitProcessUseCase {
    fun findAllDistinctUnitProcessId(requirementIdList: List<RequirementId>): List<UnitProcessId>
}