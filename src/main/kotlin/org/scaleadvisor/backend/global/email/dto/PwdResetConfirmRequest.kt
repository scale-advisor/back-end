package org.scaleadvisor.backend.global.email.dto

data class PwdResetConfirmRequest(
    val token: String,
    val newPassword: String
)
