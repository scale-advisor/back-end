package org.scaleadvisor.backend.project.application.service.requirementunitprocess

import org.scaleadvisor.backend.project.application.port.repository.requirementunitprocess.CreateRequirementUnitProcessPort
import org.scaleadvisor.backend.project.application.port.usecase.requirementunitprocess.CreateRequirementUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.RequirementUnitProcess
import org.springframework.stereotype.Service

@Service
private class CreateRequirementUnitProcessService(
    private val createRequirementUnitProcessPort: CreateRequirementUnitProcessPort,
): CreateRequirementUnitProcessUseCase {
    override fun createAll(requirementUnitProcessList: List<RequirementUnitProcess>) {
        createRequirementUnitProcessPort.createAll(requirementUnitProcessList)
    }
}