package org.scaleadvisor.backend.project.application.service.fpweights

import org.scaleadvisor.backend.project.application.port.repository.fpweights.FindFpWeightsPort
import org.scaleadvisor.backend.project.application.port.usecase.fpweights.GetFpWeightsUseCase
import org.scaleadvisor.backend.project.domain.FpWeights
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetFpWeightsService(
    private val getFpWeightsPort: FindFpWeightsPort
): GetFpWeightsUseCase {

    override fun find(projectId: ProjectId): FpWeights? {
        return getFpWeightsPort.find(projectId)
    }

}