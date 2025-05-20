package org.scaleadvisor.backend.project.application.port.usecase.projectfactor

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteProjectFactorUseCase {

    fun delete(projectId: ProjectId)

}