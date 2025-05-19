package org.scaleadvisor.backend.project.application.port.repository

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface CreateUserProjectPort {

    fun createUserProject(userId: Long, projectId: ProjectId)

}
