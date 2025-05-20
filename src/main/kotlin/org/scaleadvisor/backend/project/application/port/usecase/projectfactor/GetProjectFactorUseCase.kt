package org.scaleadvisor.backend.project.application.port.usecase.projectfactor

import org.scaleadvisor.backend.project.domain.ProjectFactor
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface GetProjectFactorUseCase {

    fun find(projectId: ProjectId): ProjectFactor?

}