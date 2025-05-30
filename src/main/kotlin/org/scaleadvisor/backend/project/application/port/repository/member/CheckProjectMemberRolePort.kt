package org.scaleadvisor.backend.project.application.port.repository.member

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface CheckProjectMemberRolePort {
    fun isOwner(userId: Long, projectId: ProjectId): Boolean
}