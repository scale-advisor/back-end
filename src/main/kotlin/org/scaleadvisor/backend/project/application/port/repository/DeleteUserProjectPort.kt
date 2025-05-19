package org.scaleadvisor.backend.project.application.port.repository

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteUserProjectPort {

    fun delete(userId: Long, projectId: ProjectId)

}
