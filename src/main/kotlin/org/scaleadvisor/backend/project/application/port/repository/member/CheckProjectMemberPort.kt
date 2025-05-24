package org.scaleadvisor.backend.project.application.port.repository.member

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface CheckProjectMemberPort {

    fun exist(userId: Long, projectId: ProjectId): Boolean

}