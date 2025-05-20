package org.scaleadvisor.backend.project.application.port.repository.fpweights

import org.scaleadvisor.backend.project.domain.FpWeights

fun interface CreateFpWeightsPort {
    fun create(fpWeights: FpWeights)
}