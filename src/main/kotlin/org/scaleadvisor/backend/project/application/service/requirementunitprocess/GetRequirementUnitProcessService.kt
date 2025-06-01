package org.scaleadvisor.backend.project.application.service.requirementunitprocess

import org.scaleadvisor.backend.project.application.port.repository.requirementunitprocess.GetRequirementUnitProcessPort
import org.scaleadvisor.backend.project.application.port.usecase.requirementunitprocess.GetRequirementUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.RequirementUnitProcess
import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class GetRequirementUnitProcessService(
    private val getRequirementUnitProcessPort: GetRequirementUnitProcessPort,
): GetRequirementUnitProcessUseCase {
    override fun findAll(requirementIdList: List<RequirementId>): List<RequirementUnitProcess> {
        return getRequirementUnitProcessPort.findAll(requirementIdList)
    }
}