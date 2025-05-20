package org.scaleadvisor.backend.project.application.port.repository.fpweights

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteFpWeightsPort {
    fun delete(projectId: ProjectId)
}