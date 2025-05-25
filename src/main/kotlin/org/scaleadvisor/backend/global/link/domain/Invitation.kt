package org.scaleadvisor.backend.global.link.domain

import java.time.LocalDateTime

data class Invitation private constructor(
    val invitationToken: String?,
    val projectId: Long,
    val expireDate: LocalDateTime
) {
    companion object {
        @JvmStatic
        fun of(
            invitationToken: String,
            projectId: Long,
            expireDate: LocalDateTime
        ): Invitation = Invitation(
            invitationToken = invitationToken,
            projectId = projectId,
            expireDate = expireDate
        )

        @JvmStatic
        fun from(
            invitationToken: String,
            projectId: Long,
            expireDate: LocalDateTime
        ): Invitation = Invitation(
            invitationToken = invitationToken,
            projectId = projectId,
            expireDate = expireDate
        )
    }
}