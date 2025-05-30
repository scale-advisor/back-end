package org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor

import org.scaleadvisor.backend.project.domain.AdjustmentFactor

fun interface CreateAdjustmentFactorUseCase {
    fun createAll(adjustmentFactorList: List<AdjustmentFactor>)
}