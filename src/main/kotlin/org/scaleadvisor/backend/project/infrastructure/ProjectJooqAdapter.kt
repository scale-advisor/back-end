package org.scaleadvisor.backend.project.infrastructure

import org.jooq.DSLContext
import org.jooq.generated.tables.Project.PROJECT
import org.scaleadvisor.backend.project.application.port.repository.CreateProjectRepository
import org.scaleadvisor.backend.project.application.port.repository.GetProjectRepository
import org.scaleadvisor.backend.project.domain.Project
import org.springframework.stereotype.Repository

@Repository
class ProjectJooqAdapter(
    private val dsl: DSLContext
) : CreateProjectRepository,
    GetProjectRepository {

    override fun createProject(project: Project): Project {
        dsl.insertInto(PROJECT)
            .set(PROJECT.PROJECT_ID, project.id)
            .set(PROJECT.NAME, project.name)
            .set(PROJECT.DESCRIPTION, project.description)
            .set(PROJECT.CREATED_AT, project.createdAt.toLocalDateTime())
            .set(PROJECT.UPDATED_AT, project.updatedAt!!.toLocalDateTime())
            .execute()

        return project
    }

    override fun findAll(userId: Long): List<Project> {
        TODO("Not yet implemented")
    }

}