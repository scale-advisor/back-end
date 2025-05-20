package org.scaleadvisor.backend.project.application.port.repository.cocomoscalefactor

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteCocomoScaleFactorPort {
    fun delete(projectId: ProjectId)
}