package org.scaleadvisor.backend.project.application.port.usecase.member

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface CheckIsProjectMemberUseCase {
    fun isProjectMember(userId: Long, projectId: ProjectId) : Boolean
}