package org.scaleadvisor.backend.project.application.port.repository.projectfactor

import org.scaleadvisor.backend.project.domain.ProjectFactor

fun interface CreateProjectFactorPort {

    fun create(projectFactor: ProjectFactor)

}