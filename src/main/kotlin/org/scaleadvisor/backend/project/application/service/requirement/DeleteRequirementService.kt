package org.scaleadvisor.backend.project.application.service.requirement

import org.scaleadvisor.backend.project.application.port.repository.requirement.DeleteRequirementPort
import org.scaleadvisor.backend.project.application.port.usecase.requirement.DeleteRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirementunitprocess.DeleteRequirementUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteRequirementService(
    private val deleteRequirementUnitProcessUseCase: DeleteRequirementUnitProcessUseCase,
    private val deleteRequirementPort: DeleteRequirementPort,
) : DeleteRequirementUseCase {

    override fun deleteAll(requirementIdList: List<RequirementId>) {
        deleteRequirementUnitProcessUseCase.deleteAll(requirementIdList)
        deleteRequirementPort.deleteAll(requirementIdList)
    }

}