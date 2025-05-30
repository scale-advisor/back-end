package org.scaleadvisor.backend.project.controller.request.member

import org.scaleadvisor.backend.project.domain.ProjectMember
import org.scaleadvisor.backend.project.domain.enum.MemberRole
import org.scaleadvisor.backend.project.domain.enum.MemberState
import java.time.LocalDateTime

data class UpdateMemberStateResponse(
    val name: String,
    val email: String,
    val projectId: Long,
    val state: MemberState,
    val role: MemberRole,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
) {
    companion object {
        @JvmStatic
        fun from(member: ProjectMember): UpdateMemberStateResponse =
            UpdateMemberStateResponse(
                name = member.name,
                email = member.email,
                projectId = member.projectId.toLong(),
                state = member.state,
                role = member.role,
                createdAt = member.createdAt,
                updatedAt = member.updatedAt
            )
    }
}