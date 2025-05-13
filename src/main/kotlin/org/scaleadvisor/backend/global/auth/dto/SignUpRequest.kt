package org.scaleadvisor.backend.global.auth.dto

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String
)