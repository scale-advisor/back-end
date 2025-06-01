package org.scaleadvisor.backend.project.application.port.usecase.requirementunitprocess

import org.scaleadvisor.backend.project.domain.RequirementUnitProcess
import org.scaleadvisor.backend.project.domain.id.RequirementId

interface GetRequirementUnitProcessUseCase {
    fun findAll(requirementIdList: List<RequirementId>): List<RequirementUnitProcess>
}