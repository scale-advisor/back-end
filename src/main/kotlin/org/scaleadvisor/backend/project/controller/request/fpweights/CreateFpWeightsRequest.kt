package org.scaleadvisor.backend.project.controller.request.fpweights

data class CreateFpWeightsRequest(
    val ilfWeight: Double,
    val eifWeight: Double,
    val eiWeight: Double,
    val eoWeight: Double,
    val eqWeight: Double
)