package org.scaleadvisor.backend.project.application.service.adjustmentfactor

import org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor.GetAdjustmentFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor.GetAdjustmentFactorUseCase
import org.scaleadvisor.backend.project.domain.AdjustmentFactor
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetAdjustmentFactorService(
    private val getAdjustmentFactorPort: GetAdjustmentFactorPort
): GetAdjustmentFactorUseCase {

    override fun findAll(projectVersion: ProjectVersion): List<AdjustmentFactor> {
        return getAdjustmentFactorPort.findAll(projectVersion.id)
    }

}