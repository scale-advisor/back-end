package org.scaleadvisor.backend.user.dto

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String
)