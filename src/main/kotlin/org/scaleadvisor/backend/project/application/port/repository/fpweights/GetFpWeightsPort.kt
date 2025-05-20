package org.scaleadvisor.backend.project.application.port.repository.fpweights

import org.scaleadvisor.backend.project.domain.FpWeights
import org.scaleadvisor.backend.project.domain.id.FpWeightsId

interface GetFpWeightsPort {
    fun findById(id: FpWeightsId): FpWeights?
}