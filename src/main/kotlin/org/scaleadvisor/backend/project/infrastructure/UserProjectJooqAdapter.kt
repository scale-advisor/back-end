package org.scaleadvisor.backend.project.infrastructure

import org.jooq.DSLContext
import org.jooq.generated.Tables.USER_PROJECT
import org.scaleadvisor.backend.project.application.port.repository.CreateUserProjectPort
import org.scaleadvisor.backend.project.application.port.repository.DeleteUserProjectPort
import org.scaleadvisor.backend.project.application.port.repository.GetUserProjectPort
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Repository

@Repository
private class UserProjectJooqAdapter(
    private val dsl: DSLContext
) : CreateUserProjectPort, GetUserProjectPort, DeleteUserProjectPort {
    override fun create(userId: Long, projectId: ProjectId) {
        dsl.insertInto(USER_PROJECT)
            .set(USER_PROJECT.USER_ID, userId)
            .set(USER_PROJECT.PROJECT_ID, projectId.toLong())
            .set(USER_PROJECT.CREATED_AT, java.time.LocalDateTime.now())
            .set(USER_PROJECT.UPDATED_AT, java.time.LocalDateTime.now())
            .execute()
    }

    override fun exist(
        userId: Long,
        projectId: ProjectId
    ): Boolean {
        return dsl.fetchExists(
            dsl.selectFrom(USER_PROJECT)
                .where(USER_PROJECT.USER_ID.eq(userId))
                .and(USER_PROJECT.PROJECT_ID.eq(projectId.toLong()))
        )
    }

    override fun delete(
        userId: Long,
        projectId: ProjectId
    ) {
        dsl.deleteFrom(USER_PROJECT)
            .where(USER_PROJECT.USER_ID.eq(userId))
            .and(USER_PROJECT.PROJECT_ID.eq(projectId.toLong()))
            .execute()
    }
}