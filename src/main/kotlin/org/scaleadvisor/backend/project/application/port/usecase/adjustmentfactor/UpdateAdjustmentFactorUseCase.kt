package org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor

import org.scaleadvisor.backend.project.application.service.adjustmentfactor.dto.UpdateAdjustmentFactorDTO

fun interface UpdateAdjustmentFactorUseCase {

    fun updateAll(adjustmentFactorDTOList: List<UpdateAdjustmentFactorDTO>)

}