package org.scaleadvisor.backend.global.link.service

import org.scaleadvisor.backend.global.exception.model.ConflictException
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.global.exception.model.UnauthorizedException
import org.scaleadvisor.backend.global.link.dto.InvitationLinkResponse
import org.scaleadvisor.backend.global.link.dto.JoinProjectResponse
import org.scaleadvisor.backend.global.link.repository.InvitationLinkRepository
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class InvitationLinkService(
    private val invitationLinkRepository: InvitationLinkRepository
) {
    fun generateInvitationLink(projectId: Long
    ): InvitationLinkResponse {
        val now = LocalDateTime.now()
        val existing = invitationLinkRepository.findByProjectId(projectId)

        return if (existing != null && existing.expireDate.isAfter(now)) {
            InvitationLinkResponse(
                "/projects/join?invitationToken=${existing.invitationToken}")
        } else {
            val newToken = UUID.randomUUID().toString()

            if (existing != null) {
                invitationLinkRepository.updateInvitation(projectId, newToken)
            } else {
                invitationLinkRepository.saveInvitation(projectId, newToken)
            }
            InvitationLinkResponse("/projects/join?invitationToken=$newToken")
        }
    }

    fun joinProjectByToken(invitationToken: String
    ): JoinProjectResponse {
        val userId = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw UnauthorizedException("만료 되거나 잘못된 인증 입니다.")

        val invitation = invitationLinkRepository.findByToken(invitationToken)
            ?: throw NotFoundException("유효하지 않은 초대 토큰입니다.")

        if (invitation.expireDate.isBefore(LocalDateTime.now())) {
            throw ConflictException("초대 링크가 만료되었습니다.")
        }

        invitationLinkRepository.joinProject(userId, invitation.projectId)

        return JoinProjectResponse(invitation.projectId.toString())
    }
}