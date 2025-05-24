package org.scaleadvisor.backend.global.email.dto

data class AcceptInvitationRequest(
    val projectId: String,
    val email: String,
    val token: String
)
