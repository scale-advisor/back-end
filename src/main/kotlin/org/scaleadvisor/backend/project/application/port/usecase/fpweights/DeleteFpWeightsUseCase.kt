package org.scaleadvisor.backend.project.application.port.usecase.fpweights

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteFpWeightsUseCase {
    fun delete(projectId: ProjectId)
}