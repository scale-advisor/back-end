package org.scaleadvisor.backend.project.application.port.usecase.projectfactor

import org.scaleadvisor.backend.project.domain.ProjectFactor

fun interface UpdateProjectFactorUseCase {

    fun update(projectFactor: ProjectFactor)

}