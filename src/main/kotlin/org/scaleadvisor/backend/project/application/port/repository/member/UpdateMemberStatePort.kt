package org.scaleadvisor.backend.project.application.port.repository.member

import org.scaleadvisor.backend.project.domain.ProjectMember
import org.scaleadvisor.backend.project.domain.enum.MemberState
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface UpdateMemberStatePort {
    fun updateState(email: String, projectId: ProjectId, newState: MemberState): ProjectMember?
}