package org.scaleadvisor.backend.global.email.dto

data class ConfirmSignupRequest(
    val email: String,
    val token: String
)
