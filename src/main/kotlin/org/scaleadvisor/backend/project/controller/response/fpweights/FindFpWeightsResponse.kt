package org.scaleadvisor.backend.project.controller.response.fpweights

import org.scaleadvisor.backend.project.domain.FpWeights

data class FindFpWeightsResponse(
    val id: String,
    val projectId: String,
    val ilfWeight: Double,
    val eifWeight: Double,
    val eiWeight: Double,
    val eoWeight: Double,
    val eqWeight: Double
) {
    companion object {
        @JvmStatic
        fun from(fpWeights: FpWeights): FindFpWeightsResponse {
            return FindFpWeightsResponse(
                id = fpWeights.fpWeightsId.toString(),
                projectId = fpWeights.projectId.toString(),
                ilfWeight = fpWeights.ilfWeight,
                eifWeight = fpWeights.eifWeight,
                eiWeight = fpWeights.eiWeight,
                eoWeight = fpWeights.eoWeight,
                eqWeight = fpWeights.eqWeight
            )
        }
    }
}