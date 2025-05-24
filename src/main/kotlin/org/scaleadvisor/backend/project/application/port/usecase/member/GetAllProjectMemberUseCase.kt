package org.scaleadvisor.backend.project.application.port.usecase.member

import org.scaleadvisor.backend.project.domain.ProjectMember
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface GetAllProjectMemberUseCase {
    fun findAllByProjectId(projectId: ProjectId, offset: Int, limit: Int
    ): List<ProjectMember>
}