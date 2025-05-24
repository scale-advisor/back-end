package org.scaleadvisor.backend.project.infrastructure

import org.jooq.DSLContext
import org.jooq.generated.Tables.PROJECT_MEMBER
import org.jooq.generated.Tables.USER
import org.jooq.impl.DSL
import org.scaleadvisor.backend.project.application.port.repository.member.CreateProjectMemberPort
import org.scaleadvisor.backend.project.application.port.repository.member.DeleteProjectMemberPort
import org.scaleadvisor.backend.project.application.port.repository.member.GetAllProjectMemberPort
import org.scaleadvisor.backend.project.application.port.repository.member.GetProjectMemberPort
import org.scaleadvisor.backend.project.domain.*
import org.scaleadvisor.backend.project.domain.enum.MemberRole
import org.scaleadvisor.backend.project.domain.enum.MemberState
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository


@Repository
private class ProjectMemberJooqAdapter(
    private val dsl: DSLContext
) : CreateProjectMemberPort,
    GetProjectMemberPort,
    DeleteProjectMemberPort,
    GetAllProjectMemberPort{

    private val log = LoggerFactory.getLogger(javaClass)

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