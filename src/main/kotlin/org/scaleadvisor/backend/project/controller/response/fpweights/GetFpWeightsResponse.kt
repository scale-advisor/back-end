package org.scaleadvisor.backend.project.controller.response.fpweights

import org.scaleadvisor.backend.project.domain.enum.FunctionType

data class GetFpWeightsResponse(
    val fpWeightList: List<FunctionWeightDTO> = FunctionType.entries
        .map { FunctionWeightDTO(it.name, it.weight) }
) {
    data class FunctionWeightDTO(
        val functionType: String,
        val weight: Double
    )

}