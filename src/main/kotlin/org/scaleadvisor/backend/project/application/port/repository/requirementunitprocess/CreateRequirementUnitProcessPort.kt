package org.scaleadvisor.backend.project.application.port.repository.requirementunitprocess

import org.scaleadvisor.backend.project.domain.RequirementUnitProcess

interface CreateRequirementUnitProcessPort {
    fun createAll(requirementUnitProcessList: List<RequirementUnitProcess>)
}