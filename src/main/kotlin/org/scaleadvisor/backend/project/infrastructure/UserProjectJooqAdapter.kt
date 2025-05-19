package org.scaleadvisor.backend.project.infrastructure

import org.jooq.DSLContext
import org.jooq.generated.Tables.USER_PROJECT
import org.scaleadvisor.backend.project.application.port.repository.CreateUserProjectRepository
import org.springframework.stereotype.Repository

@Repository
private class UserProjectJooqAdapter(
    private val dsl: DSLContext
) : CreateUserProjectRepository {
    override fun createUserProject(userId: Long, projectId: Long) {
        dsl.insertInto(USER_PROJECT)
            .set(USER_PROJECT.USER_ID, userId)
            .set(USER_PROJECT.PROJECT_ID, projectId)
            .set(USER_PROJECT.CREATED_AT, java.time.LocalDateTime.now())
            .set(USER_PROJECT.UPDATED_AT, java.time.LocalDateTime.now())
            .execute()
    }
}