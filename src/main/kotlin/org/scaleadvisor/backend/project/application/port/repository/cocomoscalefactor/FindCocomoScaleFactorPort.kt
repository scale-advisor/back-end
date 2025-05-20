package org.scaleadvisor.backend.project.application.port.repository.cocomoscalefactor

import org.scaleadvisor.backend.project.domain.CocomoScaleFactor
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface FindCocomoScaleFactorPort {
    fun findByProjectId(projectId: ProjectId): CocomoScaleFactor?
}