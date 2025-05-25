package org.scaleadvisor.backend.project.application.port.repository.member

import org.scaleadvisor.backend.project.domain.ProjectMember
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface GetAllProjectMemberPort {
    fun findAllByProjectId(projectId: ProjectId): List<ProjectMember>
}