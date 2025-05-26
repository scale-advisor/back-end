package org.scaleadvisor.backend.project.domain

import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.scaleadvisor.backend.project.domain.id.UnitProcessId

data class RequirementUnitProcess(
    val requirementId: RequirementId,
    val unitProcessId: UnitProcessId
)