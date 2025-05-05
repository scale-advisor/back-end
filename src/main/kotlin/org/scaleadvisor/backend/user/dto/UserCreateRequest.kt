package org.scaleadvisor.backend.user.dto

data class UserCreateRequest(
    val username: String,
    val email: String,
    val password: String
)