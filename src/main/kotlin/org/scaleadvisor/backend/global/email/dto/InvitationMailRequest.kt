package org.scaleadvisor.backend.global.email.dto

data class InvitationMailRequest(
    val email: String,
    val invitationUrl: String
)
