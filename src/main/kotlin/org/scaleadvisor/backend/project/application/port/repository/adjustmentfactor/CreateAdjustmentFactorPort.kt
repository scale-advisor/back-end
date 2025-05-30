package org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor

import org.scaleadvisor.backend.project.domain.AdjustmentFactor


fun interface CreateAdjustmentFactorPort {

    fun createAll(adjustmentFactorList: List<AdjustmentFactor>)

}