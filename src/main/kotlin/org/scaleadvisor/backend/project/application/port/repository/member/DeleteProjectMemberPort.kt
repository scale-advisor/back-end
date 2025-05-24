package org.scaleadvisor.backend.project.application.port.repository.member

import org.scaleadvisor.backend.project.domain.id.ProjectId

interface DeleteProjectMemberPort {
    fun delete(userId: Long, projectId: ProjectId)
    fun delete(email: String, projectId: Long)
}