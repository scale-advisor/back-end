package org.scaleadvisor.backend.project.application.service.adjustmentfactor

import org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor.UpdateAdjustmentFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor.UpdateAdjustmentFactorUseCase
import org.scaleadvisor.backend.project.application.service.adjustmentfactor.dto.UpdateAdjustmentFactorDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class UpdateAdjustmentFactorService(
    private val updateAdjustmentFactorPort: UpdateAdjustmentFactorPort,
): UpdateAdjustmentFactorUseCase {

    override fun updateAll(adjustmentFactorDTOList: List<UpdateAdjustmentFactorDTO>) {
        updateAdjustmentFactorPort.updateAll(adjustmentFactorDTOList)
    }

}