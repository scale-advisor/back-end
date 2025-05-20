package org.scaleadvisor.backend.project.application.port.repository.project

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteProjectPort {

    fun delete(projectId: ProjectId)

}