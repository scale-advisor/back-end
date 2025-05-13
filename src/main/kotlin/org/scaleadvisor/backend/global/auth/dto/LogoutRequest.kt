package org.scaleadvisor.backend.global.auth.dto

data class LogoutRequest (
    val refreshToken: String
)