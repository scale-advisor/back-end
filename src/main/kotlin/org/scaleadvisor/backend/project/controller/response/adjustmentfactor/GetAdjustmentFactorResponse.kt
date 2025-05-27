package org.scaleadvisor.backend.project.controller.response.adjustmentfactor

import org.scaleadvisor.backend.project.domain.AdjustmentFactor
import org.scaleadvisor.backend.project.domain.id.AdjustmentFactorId

data class GetAdjustmentFactorResponse(
    var adjustmentFactorList: List<GetAdjustmentFactorDTO>
) {
    data class GetAdjustmentFactorDTO(
        val adjustmentFactorId: AdjustmentFactorId,
        val adjustmentFactorType: String,
        val adjustmentFactorLevel: Int,
        val adjustmentFactorValue: String,
    ) {
        companion object {
            @JvmStatic
            fun from(adjustmentFactor: AdjustmentFactor): GetAdjustmentFactorDTO = GetAdjustmentFactorDTO(
                adjustmentFactorId = adjustmentFactor.id,
                adjustmentFactorType = adjustmentFactor.type.name,
                adjustmentFactorLevel = adjustmentFactor.level,
                adjustmentFactorValue = adjustmentFactor.value,
            )
        }
    }

    companion object {
        @JvmStatic
        fun from(adjustmentFactorList: List<AdjustmentFactor>): GetAdjustmentFactorResponse =
            GetAdjustmentFactorResponse(
                adjustmentFactorList.map {
                    GetAdjustmentFactorDTO.from(it)
                }
            )
    }
}