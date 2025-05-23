package org.scaleadvisor.backend.project.application.port.repository.member

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteProjectMemberPort {

    fun delete(userId: Long, projectId: ProjectId)

}
