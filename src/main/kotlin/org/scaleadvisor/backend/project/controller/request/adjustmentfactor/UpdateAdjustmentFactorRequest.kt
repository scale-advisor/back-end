package org.scaleadvisor.backend.project.controller.request.adjustmentfactor

data class UpdateAdjustmentFactorRequest(
    val adjustmentFactorList: List<UpdateAdjustmentFactorDTO>
) {
    data class UpdateAdjustmentFactorDTO(
        val adjustmentFactorId: Long,
        val level: Int
    )

}