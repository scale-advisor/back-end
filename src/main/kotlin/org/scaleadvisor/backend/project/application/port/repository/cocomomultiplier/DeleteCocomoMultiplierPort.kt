package org.scaleadvisor.backend.project.application.port.repository.cocomomultiplier

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteCocomoMultiplierPort {
    fun delete(projectId: ProjectId)
}