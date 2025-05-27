package org.scaleadvisor.backend.project.controller.response.adjustmentfactor

import org.scaleadvisor.backend.project.domain.enum.AdjustmentFactorType

data class GetAdjustmentFactorResponse(
    var adjustmentFactorInfoList: List<AdjustmentFactorDTO> = AdjustmentFactorType.entries.map { type ->
        val levelsDTO = (1..5).map { lvl ->
            type.info(lvl).let { LevelDTO(lvl, it.value, it.description) }
        }
        AdjustmentFactorDTO(type.name, type.koreanName, levelsDTO)
    }
) {
    data class AdjustmentFactorDTO(
        val factorType: String,
        val factorName: String,
        val levels: List<LevelDTO>
    )

    data class LevelDTO(
        val level: Int,
        val value: String,
        val description: String
    )

}