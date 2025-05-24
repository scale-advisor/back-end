package org.scaleadvisor.backend.project.domain

import org.scaleadvisor.backend.project.domain.enum.MemberRole
import org.scaleadvisor.backend.project.domain.enum.MemberState
import org.scaleadvisor.backend.project.domain.id.ProjectId
import java.time.LocalDateTime

data class ProjectMember (
    val userId: Long,
    val projectId: ProjectId,
    var state: MemberState,
    var role: MemberRole,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime?
)