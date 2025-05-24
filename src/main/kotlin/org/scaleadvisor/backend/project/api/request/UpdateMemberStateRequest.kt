package org.scaleadvisor.backend.project.api.request

import org.scaleadvisor.backend.project.domain.enum.MemberState

data class UpdateMemberStateRequest(
    val email: String,
    val newState: MemberState
)
