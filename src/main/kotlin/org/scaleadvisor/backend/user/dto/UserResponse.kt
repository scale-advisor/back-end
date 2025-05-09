package org.scaleadvisor.backend.user.dto

import org.scaleadvisor.backend.user.domain.User
import java.time.LocalDateTime

data class UserResponse(
    val userId: Long,
    val email: String,
    val name: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(user: User) = UserResponse(
            userId = user.userId!!,
            email = user.email,
            name = user.name,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt
        )
    }
}