package org.scaleadvisor.backend.project.application.port.usecase.fpweights

import org.scaleadvisor.backend.project.domain.FpWeights
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface FindFpWeightsUseCase {

    fun find(projectId: ProjectId): FpWeights?
}