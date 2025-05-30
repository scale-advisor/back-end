package org.scaleadvisor.backend.project.application.service.adjustmentfactor

import org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor.CreateAdjustmentFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor.CreateAdjustmentFactorUseCase
import org.scaleadvisor.backend.project.domain.AdjustmentFactor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class CreateAdjustmentFactorService(
    private val createAdjustmentFactorPort: CreateAdjustmentFactorPort,
): CreateAdjustmentFactorUseCase {
    override fun createAll(adjustmentFactorList: List<AdjustmentFactor>) {
        createAdjustmentFactorPort.createAll(adjustmentFactorList)
    }
}