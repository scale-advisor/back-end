package org.scaleadvisor.backend.project.infrastructure.database

import org.jooq.*
import org.jooq.generated.Tables.PROJECT_MEMBER
import org.jooq.generated.Tables.USER
import org.jooq.impl.DSL
import org.scaleadvisor.backend.project.application.port.repository.member.*
import org.scaleadvisor.backend.project.domain.ProjectMember
import org.scaleadvisor.backend.project.domain.enum.MemberRole
import org.scaleadvisor.backend.project.domain.enum.MemberState
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
private class MemberJooqAdapter(
    private val dsl: DSLContext
) : CreateProjectMemberPort,
    CheckProjectMemberPort,
    DeleteProjectMemberPort,
    GetAllProjectMemberPort,
    CheckProjectMemberRolePort,
    CheckIsEditorPort,
    CheckIsOwnerPort,
    UpdateMemberRolePort,
    UpdateMemberStatePort{

    override fun create(userId: Long, projectId: ProjectId) {
        dsl.insertInto(PROJECT_MEMBER)
            .set(PROJECT_MEMBER.USER_ID, userId)
            .set(PROJECT_MEMBER.PROJECT_ID, projectId.toLong())
            .set(PROJECT_MEMBER.STATE,      MemberState.ACCEPTED.name)
            .set(PROJECT_MEMBER.ROLE,       MemberRole.OWNER.name)
            .set(PROJECT_MEMBER.CREATED_AT, LocalDateTime.now())
            .set(PROJECT_MEMBER.UPDATED_AT, LocalDateTime.now())
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

    override fun checkIsEditor(
        projectId: Long,
        userId: Long
    ): Boolean {
        return dsl.fetchExists(
            dsl.selectFrom(PROJECT_MEMBER)
                .where(PROJECT_MEMBER.USER_ID.eq(userId))
                .and(PROJECT_MEMBER.PROJECT_ID.eq(projectId))
                .and(
                    PROJECT_MEMBER.ROLE.`in`(
                        MemberRole.OWNER.name,
                        MemberRole.EDITOR.name
                    )
                )
        )
    }

    override fun checkIsOwner(
        projectId: Long,
        userId: Long
    ): Boolean {
        return dsl.fetchExists(
            dsl.selectFrom(PROJECT_MEMBER)
                .where(PROJECT_MEMBER.USER_ID.eq(userId))
                .and(PROJECT_MEMBER.PROJECT_ID.eq(projectId))
                .and(
                    PROJECT_MEMBER.ROLE.eq(MemberRole.OWNER.name),
                )
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

    override fun delete(email: String, projectId: Long) {
        val userId = dsl.select(USER.USER_ID)
            .from(USER)
            .where(USER.EMAIL.eq(email))
            .fetchOne(USER.USER_ID)
            ?: return

        dsl.deleteFrom(PROJECT_MEMBER)
            .where(PROJECT_MEMBER.USER_ID.eq(userId))
            .and(PROJECT_MEMBER.PROJECT_ID.eq(projectId))
            .execute()
    }

    override fun findAllByProjectId(
        projectId: ProjectId
    ): List<ProjectMember> {
        val roleOrder = DSL
            .`when`(PROJECT_MEMBER.ROLE.eq(MemberRole.OWNER.name), 1)
            .`when`(PROJECT_MEMBER.ROLE.eq(MemberRole.EDITOR.name), 2)
            .`when`(PROJECT_MEMBER.ROLE.eq(MemberRole.VIEWER.name), 3)
            .otherwise(4)
            .asc()

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
                roleOrder,
                USER.NAME.asc()
            )
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

    override fun updateRole(
        email: String,
        projectId: ProjectId,
        newRole: MemberRole
    ): ProjectMember? {
        val userId = dsl.select(USER.USER_ID)
            .from(USER)
            .where(USER.EMAIL.eq(email))
            .fetchOne(USER.USER_ID)
            ?: return null

        val updated = dsl.update(PROJECT_MEMBER)
            .set(PROJECT_MEMBER.ROLE, newRole.name)
            .set(PROJECT_MEMBER.UPDATED_AT, LocalDateTime.now())
            .where(
                PROJECT_MEMBER.USER_ID.eq(userId),
                PROJECT_MEMBER.PROJECT_ID.eq(projectId.toLong())
            )
            .execute() > 0

        return if (updated) memberSelect(email = email, projectId = projectId)
            .fetchOne { rec -> mapToProjectMember(rec) }
        else null
    }

    override fun updateState(
        email: String,
        projectId: ProjectId,
        newState: MemberState
    ): ProjectMember? {
        val userId = dsl
            .select(USER.USER_ID)
            .from(USER)
            .where(USER.EMAIL.eq(email))
            .fetchOne(USER.USER_ID)
            ?: return null

        val updated = dsl.update(PROJECT_MEMBER)
            .set(PROJECT_MEMBER.STATE, newState.name)
            .set(PROJECT_MEMBER.UPDATED_AT, LocalDateTime.now())
            .where(
                PROJECT_MEMBER.USER_ID.eq(userId),
                PROJECT_MEMBER.PROJECT_ID.eq(projectId.toLong()),
                PROJECT_MEMBER.STATE.eq(MemberState.LINK_WAITING.name)
            )
            .execute() > 0

        return if (updated) {
            memberSelect(email = email, projectId = projectId)
                .fetchOne { rec -> mapToProjectMember(rec) }
        } else {
            null
        }
    }

    private fun memberSelect(
        userId: Long? = null,
        email: String? = null,
        projectId: ProjectId
    ): SelectConditionStep<Record7<String, String, Long, String, String, LocalDateTime, LocalDateTime>> {
        val conditions = mutableListOf<Condition>()
        conditions += PROJECT_MEMBER.PROJECT_ID.eq(projectId.toLong())
        userId?.let { conditions += PROJECT_MEMBER.USER_ID.eq(it) }
        email?.let { conditions += USER.EMAIL.eq(it) }

        return dsl.select(
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
            .where(*conditions.toTypedArray())
    }

    private fun mapToProjectMember(record: Record): ProjectMember =
        ProjectMember(
            name      = record.get(USER.NAME),
            email     = record.get(USER.EMAIL),
            projectId = ProjectId.from(record.get(PROJECT_MEMBER.PROJECT_ID)),
            state     = MemberState.valueOf(record.get(PROJECT_MEMBER.STATE)),
            role      = MemberRole.valueOf(record.get(PROJECT_MEMBER.ROLE)),
            createdAt = record.get(PROJECT_MEMBER.CREATED_AT),
            updatedAt = record.get(PROJECT_MEMBER.UPDATED_AT)
        )
}