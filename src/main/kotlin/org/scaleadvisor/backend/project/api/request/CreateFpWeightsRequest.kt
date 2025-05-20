package org.scaleadvisor.backend.project.api.request

data class CreateFpWeightsRequest(
    val ilfWeight: Double,
    val eifWeight: Double,
    val eiWeight: Double,
    val eoWeight: Double,
    val eqWeight: Double
)
