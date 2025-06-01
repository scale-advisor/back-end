package org.scaleadvisor.backend.project.application.port.repository.requirementunitprocess

import org.scaleadvisor.backend.project.domain.RequirementUnitProcess
import org.scaleadvisor.backend.project.domain.id.RequirementId

interface GetRequirementUnitProcessPort {
    fun findAll(requirementIdList: List<RequirementId>): List<RequirementUnitProcess>
}