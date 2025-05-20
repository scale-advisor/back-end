package org.scaleadvisor.backend.project.application.port.repository.member

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface GetUserProjectPort {

    fun exist(userId: Long, projectId: ProjectId): Boolean

}