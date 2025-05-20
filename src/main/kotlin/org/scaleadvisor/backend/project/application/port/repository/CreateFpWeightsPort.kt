package org.scaleadvisor.backend.project.application.port.repository

import org.scaleadvisor.backend.project.domain.FpWeights

interface CreateFpWeightsPort {
    fun create(fpWeights: FpWeights)
}