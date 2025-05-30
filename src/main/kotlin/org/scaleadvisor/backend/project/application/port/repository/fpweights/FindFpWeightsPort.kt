package org.scaleadvisor.backend.project.application.port.repository.fpweights

import org.scaleadvisor.backend.project.domain.FpWeights
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface FindFpWeightsPort {
    fun find(projectId: ProjectId): FpWeights?
}