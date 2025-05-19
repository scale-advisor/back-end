package org.scaleadvisor.backend.global.email.dto

data class ConfirmMailRequest(
    val email: String,
    val confirmSignupUrl: String
)
