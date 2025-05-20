package org.scaleadvisor.backend.project.application.port.repository.projectfactor

import org.scaleadvisor.backend.project.domain.ProjectFactor
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface GetProjectFactorPort {

    fun find(projectId: ProjectId): ProjectFactor?

}
