package org.scaleadvisor.backend.project.application.port.repository.cocomomultiplier

import org.scaleadvisor.backend.project.domain.CocomoMultiplier
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface FindCocomoMultiplierPort {
    fun findByProjectId(projectId: ProjectId): CocomoMultiplier?
}