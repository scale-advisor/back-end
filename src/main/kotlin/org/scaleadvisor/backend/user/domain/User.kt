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
    val confirmed: Confirmed,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val deletedAt: LocalDateTime?
) {
    companion object {
        @JvmStatic
        fun of(
            email: String,
            password: String,
            name: String,
            loginType: LoginType,
            socialId: String? = null,
            confirmed: Confirmed = Confirmed.N
        ): User {
            val now = LocalDateTime.now()
            return User(
                userId = null,
                email = email,
                password = password,
                name = name,
                socialId = socialId,
                loginType = loginType,
                confirmed = confirmed,
                createdAt = now,
                updatedAt = now,
                deletedAt = null
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
            confirmed: Confirmed,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
            deletedAt: LocalDateTime?
        ): User = User(
            userId = userId,
            email = email,
            password = password,
            name = name,
            socialId = socialId,
            loginType = loginType,
            confirmed = confirmed,
            createdAt = createdAt,
            updatedAt = updatedAt,
            deletedAt = deletedAt
        )
    }

    enum class LoginType {
        BASIC,
        KAKAO
    }

    enum class Confirmed {
        Y, N
    }
}

