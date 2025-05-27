package org.scaleadvisor.backend.project.controller.response.adjustmentfactor

import org.scaleadvisor.backend.project.domain.AdjustmentFactor
import org.scaleadvisor.backend.project.domain.id.AdjustmentFactorId

data class GetProjectAdjustmentFactorResponse(
    var adjustmentFactorList: List<GetProjectAdjustmentFactorDTO>
) {
    data class GetProjectAdjustmentFactorDTO(
        val adjustmentFactorId: AdjustmentFactorId,
        val adjustmentFactorType: String,
        val adjustmentFactorLevel: Int,
        val adjustmentFactorValue: String,
    ) {
        companion object {
            @JvmStatic
            fun from(adjustmentFactor: AdjustmentFactor): GetProjectAdjustmentFactorDTO = GetProjectAdjustmentFactorDTO(
                adjustmentFactorId = adjustmentFactor.id,
                adjustmentFactorType = adjustmentFactor.type.name,
                adjustmentFactorLevel = adjustmentFactor.level,
                adjustmentFactorValue = adjustmentFactor.value,
            )
        }
    }

    companion object {
        @JvmStatic
        fun from(adjustmentFactorList: List<AdjustmentFactor>): GetProjectAdjustmentFactorResponse =
            GetProjectAdjustmentFactorResponse(
                adjustmentFactorList.map {
                    GetProjectAdjustmentFactorDTO.from(it)
                }
            )
    }
}