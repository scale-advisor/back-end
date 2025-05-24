package org.scaleadvisor.backend.project.api.response

import org.scaleadvisor.backend.project.domain.ProjectMember
import org.scaleadvisor.backend.project.domain.enum.MemberRole
import org.scaleadvisor.backend.project.domain.enum.MemberState
import java.time.LocalDateTime

data class GetAllProjectMemberResponse(
    val members: List<Member>
) {

    data class Member(
        val name: String,
        val email: String,
        val projectId: String,
        val state: MemberState,
        val role: MemberRole,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime?
    )

    companion object {
        fun from(domainList: List<ProjectMember>): GetAllProjectMemberResponse =
            GetAllProjectMemberResponse(
                members = domainList.map { pm ->
                    Member(
                        name      = pm.name,
                        email     = pm.email,
                        projectId = pm.projectId.toString(),
                        state     = pm.state,
                        role      = pm.role,
                        createdAt = pm.createdAt,
                        updatedAt = pm.updatedAt
                    )
                }
            )
    }
}