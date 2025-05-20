package org.scaleadvisor.backend.project.api.request

data class UpdateFpWeightsRequest(
    val ilfWeight: Double,
    val eifWeight: Double,
    val eiWeight: Double,
    val eoWeight: Double,
    val eqWeight: Double
)
