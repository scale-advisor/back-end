package org.scaleadvisor.backend.project.application.service.adjustmentfactor.dto

import org.scaleadvisor.backend.project.domain.id.AdjustmentFactorId

data class UpdateAdjustmentFactorDTO(
    val id: AdjustmentFactorId,
    val level: Int,
)