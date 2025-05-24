package org.scaleadvisor.backend.project.controller.request.fpweights

data class UpdateFpWeightsRequest(
    val ilfWeight: Double,
    val eifWeight: Double,
    val eiWeight: Double,
    val eoWeight: Double,
    val eqWeight: Double
)