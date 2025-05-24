package org.scaleadvisor.backend.global.link.repository

import org.jooq.DSLContext
import org.jooq.generated.Tables.PROJECT_MEMBER
import org.scaleadvisor.backend.global.exception.model.ConflictException
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.domain.enum.MemberRole
import org.scaleadvisor.backend.project.domain.enum.MemberState
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class InvitationLinkRepository (
    private val dsl: DSLContext
){
    fun joinProjectByInvitation(
        userId: Long,
        projectId: Long
    ) {
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
            .set(PROJECT_MEMBER.STATE,      MemberState.WAITING.name)
            .set(PROJECT_MEMBER.CREATED_AT, now)
            .set(PROJECT_MEMBER.UPDATED_AT, now)
            .execute()
    }
}