package org.scaleadvisor.backend.project.application.port.repository.member

import org.scaleadvisor.backend.project.domain.enum.MemberState
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface UpdateProjectMemberStatePort {
    fun updateState(userId: Long, projectId: ProjectId, newState: MemberState): MemberState?
}