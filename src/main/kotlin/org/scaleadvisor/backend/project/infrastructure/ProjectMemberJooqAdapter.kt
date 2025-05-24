package org.scaleadvisor.backend.project.infrastructure

import org.jooq.DSLContext
import org.jooq.generated.Tables.PROJECT_MEMBER
import org.jooq.generated.Tables.USER
import org.scaleadvisor.backend.project.application.port.repository.member.*
import org.scaleadvisor.backend.project.domain.*
import org.scaleadvisor.backend.project.domain.enum.MemberRole
import org.scaleadvisor.backend.project.domain.enum.MemberState
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
private class ProjectMemberJooqAdapter(
    private val dsl: DSLContext
) : CreateProjectMemberPort,
    GetProjectMemberPort,
    DeleteProjectMemberPort,
    GetAllProjectMemberPort,
    CheckProjectMemberRolePort,
    UpdateProjectMemberStatePort{

    override fun create(userId: Long, projectId: ProjectId) {
        dsl.insertInto(PROJECT_MEMBER)
            .set(PROJECT_MEMBER.USER_ID, userId)
            .set(PROJECT_MEMBER.PROJECT_ID, projectId.toLong())
            .set(PROJECT_MEMBER.STATE,      MemberState.ACCEPTED.name)
            .set(PROJECT_MEMBER.ROLE,       MemberRole.OWNER.name)
            .set(PROJECT_MEMBER.CREATED_AT, java.time.LocalDateTime.now())
            .set(PROJECT_MEMBER.UPDATED_AT, java.time.LocalDateTime.now())
            .execute()
    }

    override fun exist(
        userId: Long,
        projectId: ProjectId
    ): Boolean {
        return dsl.fetchExists(
            dsl.selectFrom(PROJECT_MEMBER)
                .where(PROJECT_MEMBER.USER_ID.eq(userId))
                .and(PROJECT_MEMBER.PROJECT_ID.eq(projectId.toLong()))
        )
    }

    override fun delete(
        userId: Long,
        projectId: ProjectId
    ) {
        dsl.deleteFrom(PROJECT_MEMBER)
            .where(PROJECT_MEMBER.USER_ID.eq(userId))
            .and(PROJECT_MEMBER.PROJECT_ID.eq(projectId.toLong()))
            .execute()
    }

    override fun findAllByProjectId(
        projectId: ProjectId,
        offset: Int,
        limit: Int
    ): List<ProjectMember> {
        return dsl
            .select(
                USER.NAME,
                USER.EMAIL,
                PROJECT_MEMBER.PROJECT_ID,
                PROJECT_MEMBER.STATE,
                PROJECT_MEMBER.ROLE,
                PROJECT_MEMBER.CREATED_AT,
                PROJECT_MEMBER.UPDATED_AT
            )
            .from(PROJECT_MEMBER)
            .join(USER).on(PROJECT_MEMBER.USER_ID.eq(USER.USER_ID))
            .where(PROJECT_MEMBER.PROJECT_ID.eq(projectId.toLong()))
            .orderBy(
                PROJECT_MEMBER.ROLE.desc(),
                USER.NAME.asc()
            )
            .offset(offset)
            .limit(limit)
            .fetch { record -> mapToProjectMember(record) }
    }

    override fun isOwner(userId: Long, projectId: ProjectId): Boolean {
        return dsl.fetchExists(
            dsl.selectFrom(PROJECT_MEMBER)
                .where(PROJECT_MEMBER.USER_ID.eq(userId))
                .and(PROJECT_MEMBER.PROJECT_ID.eq(projectId.toLong()))
                .and(PROJECT_MEMBER.ROLE.eq(MemberRole.OWNER.name))
        )
    }

    override fun updateState(
        userId: Long,
        projectId: ProjectId,
        newState: MemberState
    ): MemberState? {
        val updatedCount = dsl.update(PROJECT_MEMBER)
            .set(PROJECT_MEMBER.STATE, newState.name)
            .set(PROJECT_MEMBER.UPDATED_AT, LocalDateTime.now())
            .where(PROJECT_MEMBER.USER_ID.eq(userId))
            .and(PROJECT_MEMBER.PROJECT_ID.eq(projectId.toLong()))
            .and(PROJECT_MEMBER.STATE.eq(MemberState.WAITING.name))
            .execute()

        return if (updatedCount > 0) newState else null
    }

    private fun mapToProjectMember(record: org.jooq.Record): ProjectMember =
        ProjectMember(
            name      = record.get(USER.NAME),
            email     = record.get(USER.EMAIL),
            projectId = ProjectId.of(record.get(PROJECT_MEMBER.PROJECT_ID)),
            state     = MemberState.valueOf(record.get(PROJECT_MEMBER.STATE)),
            role      = MemberRole.valueOf(record.get(PROJECT_MEMBER.ROLE)),
            createdAt = record.get(PROJECT_MEMBER.CREATED_AT),
            updatedAt = record.get(PROJECT_MEMBER.UPDATED_AT)
        )
}