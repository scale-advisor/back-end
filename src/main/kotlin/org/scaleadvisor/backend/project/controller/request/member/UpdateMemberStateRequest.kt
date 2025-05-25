package org.scaleadvisor.backend.project.controller.request.member

import org.scaleadvisor.backend.project.domain.enum.MemberState

data class UpdateMemberStateRequest(
    val email: String,
    val newState: MemberState
)
