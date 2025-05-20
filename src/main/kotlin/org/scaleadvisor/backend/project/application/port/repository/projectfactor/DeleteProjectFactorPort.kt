package org.scaleadvisor.backend.project.application.port.repository.projectfactor

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteProjectFactorPort {

    fun delete(projectId: ProjectId)

}