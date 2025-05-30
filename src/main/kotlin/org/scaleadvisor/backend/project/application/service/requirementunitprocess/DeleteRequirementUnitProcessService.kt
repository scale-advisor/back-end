package org.scaleadvisor.backend.project.application.service.requirementunitprocess

import org.scaleadvisor.backend.project.application.port.repository.requirementunitprocess.DeleteRequirementUnitProcessPort
import org.scaleadvisor.backend.project.application.port.usecase.requirementunitprocess.DeleteRequirementUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.springframework.stereotype.Service

@Service
private class DeleteRequirementUnitProcessService(
    private val deleteRequirementUnitProcessPort: DeleteRequirementUnitProcessPort,
): DeleteRequirementUnitProcessUseCase {
    override fun deleteAll(requirementIdList: List<RequirementId>) {
        return deleteRequirementUnitProcessPort.deleteAll(requirementIdList)
    }

}