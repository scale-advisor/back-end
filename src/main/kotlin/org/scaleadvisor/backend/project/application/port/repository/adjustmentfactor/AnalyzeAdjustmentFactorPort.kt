package org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor

fun interface AnalyzeAdjustmentFactorPort {
    operator fun invoke(prompt: String): String
}