package org.scaleadvisor.backend.project.infrastructure.database

import org.jooq.DSLContext
import org.jooq.generated.Tables.PROJECT
import org.jooq.generated.Tables.PROJECT_MEMBER
import org.jooq.generated.tables.records.ProjectRecord
import org.scaleadvisor.backend.project.application.port.repository.project.CreateProjectPort
import org.scaleadvisor.backend.project.application.port.repository.project.DeleteProjectPort
import org.scaleadvisor.backend.project.application.port.repository.project.GetProjectPort
import org.scaleadvisor.backend.project.application.port.repository.project.UpdateProjectPort
import org.scaleadvisor.backend.project.domain.Project
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Repository

@Repository
private class ProjectJooqAdapter(
    private val dsl: DSLContext
) : CreateProjectPort, GetProjectPort, UpdateProjectPort, DeleteProjectPort {

    private fun ProjectRecord.toDomain() = Project(
        id = ProjectId.of(this.projectId),
        name = this.name,
        description = this.description,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )

    override fun create(project: Project) {
        dsl.insertInto(PROJECT)
            .set(PROJECT.PROJECT_ID, project.id.toLong())
            .set(PROJECT.NAME, project.name)
            .set(PROJECT.DESCRIPTION, project.description)
            .set(PROJECT.CREATED_AT, project.createdAt)
            .set(PROJECT.UPDATED_AT, project.updatedAt!!)
            .execute()
    }

    override fun find(projectId: ProjectId): Project? {
        return dsl
            .selectFrom(PROJECT)
            .where(PROJECT.PROJECT_ID.eq(projectId.toLong()))
            .fetchOne { record -> record.into(PROJECT).toDomain() }
    }

    override fun findAll(userId: Long): List<Project> {
        return dsl
            .select(
                PROJECT.PROJECT_ID,
                PROJECT.NAME,
                PROJECT.DESCRIPTION,
                PROJECT.CREATED_AT,
                PROJECT.UPDATED_AT
            )
            .from(PROJECT)
            .innerJoin(PROJECT_MEMBER)
            .on(
                PROJECT_MEMBER.PROJECT_ID.eq(PROJECT.PROJECT_ID)
                    .and(PROJECT_MEMBER.USER_ID.eq(userId))
            )
            .fetch { record -> record.into(PROJECT).toDomain() }
    }

    override fun update(project: Project) {
        dsl
            .update(PROJECT)
            .set(PROJECT.NAME, project.name)
            .set(PROJECT.DESCRIPTION, project.description)
            .set(PROJECT.UPDATED_AT, project.updatedAt!!)
            .where(PROJECT.PROJECT_ID.eq(project.id.toLong()))
            .execute()
    }

    override fun delete(projectId: ProjectId) {
        dsl
            .deleteFrom(PROJECT)
            .where(PROJECT.PROJECT_ID.eq(projectId.toLong()))
            .execute()
    }

}