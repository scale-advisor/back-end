package org.scaleadvisor.backend.project.application.port.usecase.cocomomultiplier

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteCocomoMultiplierUseCase {
    fun delete(projectId: ProjectId)
}