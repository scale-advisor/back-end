package org.scaleadvisor.backend.project.infrastructure

import org.jooq.DSLContext
import org.jooq.generated.Tables.PROJECT_MEMBER
import org.scaleadvisor.backend.project.application.port.repository.member.CreateProjectMemberPort
import org.scaleadvisor.backend.project.application.port.repository.member.DeleteProjectMemberPort
import org.scaleadvisor.backend.project.application.port.repository.member.GetProjectMemberPort
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Repository

@Repository
private class ProjectMemberMemberMemberJooqAdapter(
    private val dsl: DSLContext
) : CreateProjectMemberPort, GetProjectMemberPort, DeleteProjectMemberPort {
    override fun create(userId: Long, projectId: ProjectId) {
        dsl.insertInto(PROJECT_MEMBER)
            .set(PROJECT_MEMBER.USER_ID, userId)
            .set(PROJECT_MEMBER.PROJECT_ID, projectId.toLong())
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
}