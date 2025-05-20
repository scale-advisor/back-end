package org.scaleadvisor.backend.project.application.port.usecase.fpweights

import org.scaleadvisor.backend.project.domain.FpWeights
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface UpdateFpWeightsUseCase {
    class UpdateFpWeightsCommand(
        val projectId: ProjectId,
        val ilfWeight: Double,
        val eifWeight: Double,
        val eiWeight: Double,
        val eoWeight: Double,
        val eqWeight: Double
    )

    fun update(command: UpdateFpWeightsCommand): FpWeights
}