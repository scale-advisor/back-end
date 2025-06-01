package org.scaleadvisor.backend.project.application.port.repository.requirementunitprocess

import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.scaleadvisor.backend.project.domain.id.UnitProcessId

interface DeleteRequirementUnitProcessPort {
    fun deleteAll(requirementIdList: List<RequirementId>)
    fun deleteAll(unitProcessIdList: List<UnitProcessId>)
}