package org.scaleadvisor.backend.project.application.port.usecase.cocomoscalefactor

import org.scaleadvisor.backend.project.domain.CocomoScaleFactor
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface FindCocomoScaleFactorUseCase {
    fun find(projectId: ProjectId): CocomoScaleFactor?
}