package org.scaleadvisor.backend.project.application.port.repository.requirementunitprocess

import org.scaleadvisor.backend.project.domain.id.RequirementId

interface DeleteRequirementUnitProcessPort {
    fun deleteAll(requirementIdList: List<RequirementId>)
}