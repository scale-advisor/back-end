package org.scaleadvisor.backend.project.application.port.usecase.cocomoscalefactor

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteCocomoScaleFactorUseCase {
    fun delete(projectId: ProjectId)
}