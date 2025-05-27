package org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor

import org.scaleadvisor.backend.project.application.service.adjustmentfactor.dto.UpdateAdjustmentFactorDTO

fun interface UpdateAdjustmentFactorPort {

    fun updateAll(command: List<UpdateAdjustmentFactorDTO>)

}