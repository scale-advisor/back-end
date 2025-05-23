package org.scaleadvisor.backend.project.application.port.repository.member

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface CreateProjectMemberPort {

    fun create(userId: Long, projectId: ProjectId)

}