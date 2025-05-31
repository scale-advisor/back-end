package org.scaleadvisor.backend.global.auth.dto

data class LoginResponse(
    val name: String,
    val email: String,
    val accessToken: String
)