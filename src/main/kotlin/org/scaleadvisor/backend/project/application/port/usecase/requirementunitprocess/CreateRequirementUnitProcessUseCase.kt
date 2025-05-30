package org.scaleadvisor.backend.project.application.port.usecase.requirementunitprocess

import org.scaleadvisor.backend.project.domain.RequirementUnitProcess

interface CreateRequirementUnitProcessUseCase {
    fun createAll(requirementUnitProcessList: List<RequirementUnitProcess>)
}