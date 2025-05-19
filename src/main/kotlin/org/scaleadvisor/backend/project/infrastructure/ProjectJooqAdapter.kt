package org.scaleadvisor.backend.project.infrastructure

import org.jooq.DSLContext
import org.jooq.generated.tables.Project.PROJECT
import org.jooq.generated.tables.UserProject.USER_PROJECT
import org.jooq.generated.tables.records.ProjectRecord
import org.scaleadvisor.backend.project.application.port.repository.CreateProjectRepository
import org.scaleadvisor.backend.project.application.port.repository.GetProjectRepository
import org.scaleadvisor.backend.project.domain.Project
import org.springframework.stereotype.Repository

@Repository
private class ProjectJooqAdapter(
    private val dsl: DSLContext
) : CreateProjectRepository, GetProjectRepository {

    private fun ProjectRecord.toDomain() = Project(
        id = this.projectId,
        name = this.name,
        description = this.description,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )

    override fun createProject(project: Project): Project {
        dsl.insertInto(PROJECT)
            .set(PROJECT.PROJECT_ID, project.id)
            .set(PROJECT.NAME, project.name)
            .set(PROJECT.DESCRIPTION, project.description)
            .set(PROJECT.CREATED_AT, project.createdAt)
            .set(PROJECT.UPDATED_AT, project.updatedAt!!)
            .execute()

        return project
    }

    override fun findAll(userId: Long): List<Project> {
        return dsl
            .select(PROJECT.PROJECT_ID,
                PROJECT.NAME,
                PROJECT.DESCRIPTION,
                PROJECT.CREATED_AT,
                PROJECT.UPDATED_AT)
            .from(PROJECT)
            .innerJoin(USER_PROJECT)
            .on(USER_PROJECT.PROJECT_ID.eq(PROJECT.PROJECT_ID)
                .and(USER_PROJECT.USER_ID.eq(userId)))
            .fetch{record -> record.into(PROJECT).toDomain()}
    }

}