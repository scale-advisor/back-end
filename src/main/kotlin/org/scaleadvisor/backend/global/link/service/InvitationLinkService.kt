package org.scaleadvisor.backend.global.link.service

import org.scaleadvisor.backend.global.exception.model.EmailTokenGoneException
import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.global.link.dto.InvitationLinkResponse
import org.scaleadvisor.backend.global.link.repository.InvitationLinkRepository
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.*

@Service
class InvitationLinkService(
    private val redisTemplate: RedisTemplate<String, String>,
    private val invitationLinkRepository: InvitationLinkRepository
) {
    private val TOKEN_TTL_SECONDS = 600L
    private val valueOps: ValueOperations<String, String>
        get() = redisTemplate.opsForValue()

    fun generateInvitationLink(projectId: Long): InvitationLinkResponse {
        val token = UUID.randomUUID().toString()
        val key   = "invitation:token:$token"
        valueOps.set(key, projectId.toString(), Duration.ofSeconds(TOKEN_TTL_SECONDS))

        val link = "/projects/$projectId/join?invitationToken=$token"
        return InvitationLinkResponse(
            invitationLink   = link
        )
    }

    fun joinProjectByToken(projectId: Long, invitationToken: String) {
        val key = "invitation:token:$invitationToken"
        val stored = valueOps.get(key)
            ?: throw EmailTokenGoneException("링크 토큰이 만료되었거나 유효하지 않습니다.")
        val storedProjectId = stored.toLongOrNull()
            ?: throw EmailTokenGoneException("링크 토큰 데이터가 손상되었습니다.")

        if (storedProjectId != projectId) {
            throw EmailTokenGoneException("링크 토큰의 프로젝트 정보가 일치하지 않습니다.")
        }

        redisTemplate.delete(key)

        val userId = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw ForbiddenException("로그인이 필요합니다.")

        invitationLinkRepository.joinProjectByInvitation(
            userId = userId,
            projectId = projectId
        )
    }
}