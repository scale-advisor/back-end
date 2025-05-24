package org.scaleadvisor.backend.project.controller.request.member

import org.scaleadvisor.backend.project.domain.enum.MemberRole

data class UpdateMemberRoleRequest(
    val email: String,
    val newRole: MemberRole
)