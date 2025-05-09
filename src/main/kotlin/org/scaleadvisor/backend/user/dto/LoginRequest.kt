package org.scaleadvisor.backend.user.dto

data class LoginRequest(
    val email: String,
    val password: String
)