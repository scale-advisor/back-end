package org.scaleadvisor.backend.global.link.repository

import org.jooq.DSLContext
import org.jooq.generated.Tables.INVITATION
import org.jooq.generated.Tables.PROJECT_MEMBER
import org.scaleadvisor.backend.global.exception.model.ConflictException
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.global.link.domain.Invitation
import org.scaleadvisor.backend.project.domain.enum.MemberRole
import org.scaleadvisor.backend.project.domain.enum.MemberState
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Repository
class InvitationLinkRepository (
    private val dsl: DSLContext
){
    fun findByProjectId(projectId: Long): Invitation? {
        val r = dsl.select(
            INVITATION.INVITATION_TOKEN,
            INVITATION.PROJECT_ID,
            INVITATION.EXPIRE_DATE
        )
            .from(INVITATION)
            .where(INVITATION.PROJECT_ID.eq(projectId))
            .fetchOne() ?: return null

        return Invitation.from(
            invitationToken = r.get(INVITATION.INVITATION_TOKEN),
            projectId = r.get(INVITATION.PROJECT_ID),
            expireDate = r.get(INVITATION.EXPIRE_DATE)
        )
    }

    fun saveInvitation(
        projectId: Long,
        token: String
    ) {
        val expireDate = LocalDateTime.now().plus(24, ChronoUnit.HOURS)

        val exists = dsl.fetchExists(
            dsl.selectFrom(INVITATION)
                .where(INVITATION.PROJECT_ID.eq(projectId))
                .and(INVITATION.INVITATION_TOKEN.eq(token))
        )

        if (exists) {
            throw ConflictException("이미 존재하는 초대 링크입니다.")
        }

        dsl.insertInto(INVITATION)
            .set(INVITATION.INVITATION_TOKEN, token)
            .set(INVITATION.PROJECT_ID, projectId)
            .set(INVITATION.EXPIRE_DATE, expireDate)
            .execute()
    }

    fun updateInvitation(
        projectId: Long,
        newToken: String
    ) {
        val newExpire = LocalDateTime.now().plus(24, ChronoUnit.HOURS)
        val updatedCount = dsl.update(INVITATION)
            .set(INVITATION.INVITATION_TOKEN, newToken)
            .set(INVITATION.EXPIRE_DATE, newExpire)
            .where(INVITATION.PROJECT_ID.eq(projectId))
            .execute()

        if (updatedCount != 1) {
            throw NotFoundException("갱신할 초대 링크가 없습니다.")
        }
    }

    fun findByToken(token: String): Invitation? {
        val r = dsl.select(
            INVITATION.INVITATION_TOKEN,
            INVITATION.PROJECT_ID,
            INVITATION.EXPIRE_DATE
        )
            .from(INVITATION)
            .where(INVITATION.INVITATION_TOKEN.eq(token))
            .fetchOne() ?: return null

        return Invitation.from(
            invitationToken = r.get(INVITATION.INVITATION_TOKEN),
            projectId = r.get(INVITATION.PROJECT_ID),
            expireDate = r.get(INVITATION.EXPIRE_DATE)
        )
    }

    fun joinProject(userId: Long, projectId: Long) {
        val exists = dsl.fetchExists(
            dsl.selectFrom(PROJECT_MEMBER)
                .where(PROJECT_MEMBER.USER_ID.eq(userId))
                .and(PROJECT_MEMBER.PROJECT_ID.eq(projectId))
        )
        if (exists) {
            throw ConflictException("이미 프로젝트에 참여 중입니다.")
        }

        val now = LocalDateTime.now()
        dsl.insertInto(PROJECT_MEMBER)
            .set(PROJECT_MEMBER.USER_ID,    userId)
            .set(PROJECT_MEMBER.PROJECT_ID, projectId)
            .set(PROJECT_MEMBER.ROLE,       MemberRole.VIEWER.name)
            .set(PROJECT_MEMBER.STATE,      MemberState.LINK_WAITING.name)
            .set(PROJECT_MEMBER.CREATED_AT, now)
            .set(PROJECT_MEMBER.UPDATED_AT, now)
            .execute()
    }
}