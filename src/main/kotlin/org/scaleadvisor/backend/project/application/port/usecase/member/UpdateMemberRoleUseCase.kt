package org.scaleadvisor.backend.project.application.port.usecase.member

import org.scaleadvisor.backend.project.domain.ProjectMember
import org.scaleadvisor.backend.project.domain.enum.MemberRole
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface UpdateMemberRoleUseCase {
    data class UpdateMemberRoleCommand(
        val email: String,
        val projectId: ProjectId,
        val newRole: MemberRole
    )

    fun update(command: UpdateMemberRoleCommand): ProjectMember?
}