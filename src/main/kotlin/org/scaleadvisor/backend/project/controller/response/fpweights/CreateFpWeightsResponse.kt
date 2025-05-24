package org.scaleadvisor.backend.project.controller.response.fpweights

import org.scaleadvisor.backend.project.domain.FpWeights
import java.time.LocalDateTime

data class CreateFpWeightsResponse(
    val id: String,
    val projectId: String,
    val ilfWeight: Double,
    val eifWeight: Double,
    val eiWeight: Double,
    val eoWeight: Double,
    val eqWeight: Double,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        @JvmStatic
        fun from(fpWeights: FpWeights): CreateFpWeightsResponse {
            return CreateFpWeightsResponse(
                id = fpWeights.fpWeightsId.toString(),
                projectId = fpWeights.projectId.toString(),
                ilfWeight = fpWeights.ilfWeight,
                eifWeight = fpWeights.eifWeight,
                eiWeight = fpWeights.eiWeight,
                eoWeight = fpWeights.eoWeight,
                eqWeight = fpWeights.eqWeight,
                createdAt = fpWeights.createdAt,
                updatedAt = fpWeights.updatedAt ?: fpWeights.createdAt
            )
        }
    }
}