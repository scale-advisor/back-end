package org.scaleadvisor.backend.project.application.service.requirementunitprocess

import org.scaleadvisor.backend.project.application.port.repository.requirementunitprocess.GetRequirementUnitProcessPort
import org.scaleadvisor.backend.project.application.port.usecase.requirementunitprocess.GetRequirementUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.scaleadvisor.backend.project.domain.id.UnitProcessId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetRequirementUnitProcessService(
    private val getRequirementUnitProcessPort: GetRequirementUnitProcessPort,
): GetRequirementUnitProcessUseCase {
    override fun findAllDistinctUnitProcessId(requirementIdList: List<RequirementId>): List<UnitProcessId> {
        return getRequirementUnitProcessPort.findAllDistinctUnitProcessId(requirementIdList)
    }
}