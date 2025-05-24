package org.scaleadvisor.backend.project.application.port.repository.member

import org.scaleadvisor.backend.project.domain.ProjectMember
import org.scaleadvisor.backend.project.domain.enum.MemberRole
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface UpdateMemberRolePort {
    fun updateRole(email: String, projectId: ProjectId, newRole: MemberRole
    ): ProjectMember?
}