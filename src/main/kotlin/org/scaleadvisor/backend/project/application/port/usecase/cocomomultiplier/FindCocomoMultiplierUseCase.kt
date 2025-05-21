package org.scaleadvisor.backend.project.application.port.usecase.cocomomultiplier

import org.scaleadvisor.backend.project.domain.CocomoMultiplier
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface FindCocomoMultiplierUseCase {
    fun find(projectId: ProjectId): CocomoMultiplier?
}