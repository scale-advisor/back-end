package org.scaleadvisor.backend.global.email.repository

import org.jooq.DSLContext
import org.jooq.generated.Tables.PROJECT_MEMBER
import org.jooq.generated.Tables.USER
import org.scaleadvisor.backend.global.exception.model.ConflictException
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.domain.enum.MemberRole
import org.scaleadvisor.backend.project.domain.enum.MemberState
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class InvitationEmailRepository(
    private val dsl: DSLContext
) {
    fun isOwner(userId: Long, projectId: Long): Boolean {
        return dsl.fetchExists(
            dsl.selectFrom(PROJECT_MEMBER)
                .where(PROJECT_MEMBER.USER_ID.eq(userId))
                .and(PROJECT_MEMBER.PROJECT_ID.eq(projectId))
                .and(PROJECT_MEMBER.ROLE.eq(MemberRole.OWNER.name))
        )
    }

    fun inviteByEmail(
        email: String,
        projectId: Long
    ) {
        val userId = dsl.select(USER.USER_ID)
            .from(USER)
            .where(USER.EMAIL.eq(email))
            .fetchOne(USER.USER_ID)
            ?: throw NotFoundException("해당 이메일의 유저가 존재하지 않습니다.")

        val already = dsl.fetchExists(
            dsl.selectFrom(PROJECT_MEMBER)
                .where(PROJECT_MEMBER.USER_ID.eq(userId))
                .and(PROJECT_MEMBER.PROJECT_ID.eq(projectId))
        )
        if (already) {
            throw ConflictException("해당 유저는 이미 프로젝트에 초대되어 있습니다.")
        }

        dsl.insertInto(PROJECT_MEMBER)
            .set(PROJECT_MEMBER.USER_ID, userId)
            .set(PROJECT_MEMBER.PROJECT_ID, projectId)
            .set(PROJECT_MEMBER.ROLE, MemberRole.VIEWER.name)
            .set(PROJECT_MEMBER.STATE, MemberState.EMAIL_WAITING.name)
            .set(PROJECT_MEMBER.CREATED_AT, LocalDateTime.now())
            .set(PROJECT_MEMBER.UPDATED_AT, LocalDateTime.now())
            .execute()
    }

    fun acceptInvitation(
        email: String,
        projectId: Long
    ) {
        val userId = dsl
            .select(USER.USER_ID)
            .from(USER)
            .where(USER.EMAIL.eq(email))
            .fetchOne(USER.USER_ID)
            ?: throw NotFoundException("해당 이메일의 유저가 존재하지 않습니다.")

        val updatedCount = dsl
            .update(PROJECT_MEMBER)
            .set(PROJECT_MEMBER.STATE, MemberState.ACCEPTED.name)
            .set(PROJECT_MEMBER.UPDATED_AT, LocalDateTime.now())
            .where(
                PROJECT_MEMBER.USER_ID.eq(userId),
                PROJECT_MEMBER.PROJECT_ID.eq(projectId),
                PROJECT_MEMBER.STATE.eq(MemberState.EMAIL_WAITING.name)
            )
            .execute()

        if (updatedCount == 0) {
            throw NotFoundException("해당 이메일의 프로젝트 초대 정보가 없거나 이미 처리되었습니다.")
        }
    }
}