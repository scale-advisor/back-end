package org.scaleadvisor.backend.user.dto

data class UserResponse(
    val id: Long,
    val username: String,
    val email: String
)