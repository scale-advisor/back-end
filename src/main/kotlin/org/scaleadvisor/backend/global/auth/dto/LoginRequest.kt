package org.scaleadvisor.backend.global.auth.dto

data class LoginRequest(
    val email: String,
    val password: String
)