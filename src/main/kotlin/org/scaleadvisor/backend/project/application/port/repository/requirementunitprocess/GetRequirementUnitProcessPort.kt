package org.scaleadvisor.backend.project.application.port.repository.requirementunitprocess

import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.scaleadvisor.backend.project.domain.id.UnitProcessId

interface GetRequirementUnitProcessPort {
    fun findAllDistinctUnitProcessId(requirementIdList: List<RequirementId>): List<UnitProcessId>
}