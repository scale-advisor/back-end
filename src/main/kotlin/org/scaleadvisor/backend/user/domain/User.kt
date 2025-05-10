package org.scaleadvisor.backend.user.domain

import lombok.Getter
import java.time.LocalDateTime

@Getter
data class User private constructor(
    val userId: Long?,
    val email: String,
    val password: String,
    val name: String,
    val socialId: String?,
    val loginType: LoginType,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        @JvmStatic
        fun of(
            email: String,
            password: String,
            name: String,
            loginType: LoginType,
            socialId: String? = null
        ): User {
            val now = LocalDateTime.now()
            return User(
                userId = null,
                email = email,
                password = password,
                name = name,
                socialId = socialId,
                loginType = loginType,
                createdAt = now,
                updatedAt = now
            )
        }

        @JvmStatic
        fun fromDb(
            userId: Long,
            email: String,
            password: String,
            name: String,
            socialId: String?,
            loginType: LoginType,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime
        ): User = User(
            userId = userId,
            email = email,
            password = password,
            name = name,
            socialId = socialId,
            loginType = loginType,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    enum class LoginType {
        BASIC,
        KAKAO
    }
}

