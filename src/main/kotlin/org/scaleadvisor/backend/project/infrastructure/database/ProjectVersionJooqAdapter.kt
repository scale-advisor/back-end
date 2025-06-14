package org.scaleadvisor.backend.project.infrastructure.database

import org.jooq.DSLContext
import org.jooq.generated.Tables.VERSION
import org.jooq.generated.tables.records.VersionRecord
import org.scaleadvisor.backend.project.application.port.repository.version.CreateProjectVersionPort
import org.scaleadvisor.backend.project.application.port.repository.version.DeleteProjectVersionPort
import org.scaleadvisor.backend.project.application.port.repository.version.GetProjectVersionPort
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
private class ProjectVersionJooqAdapter(
    private val dsl: DSLContext
) : CreateProjectVersionPort, GetProjectVersionPort, DeleteProjectVersionPort {
    private fun VersionRecord.toDomain() = ProjectVersion.of(
        projectId = ProjectId.from(this.projectId),
        major = this.versionMajorNumber,
        minor = this.versionMinorNumber
    )

    override fun create(projectVersion: ProjectVersion) {
        dsl
            .insertInto(VERSION)
            .set(VERSION.PROJECT_ID, projectVersion.projectId.toLong())
            .set(VERSION.VERSION_MAJOR_NUMBER, projectVersion.major)
            .set(VERSION.VERSION_MINOR_NUMBER, projectVersion.minor)
            .set(VERSION.CREATED_AT, LocalDateTime.now())
            .execute()
    }

    override fun findOrderByVersionNumberDesc(projectId: ProjectId): ProjectVersion? {
        return dsl
            .selectFrom(VERSION)
            .where(VERSION.PROJECT_ID.eq(projectId.toLong()))
            .orderBy(VERSION.VERSION_MAJOR_NUMBER.desc(), VERSION.VERSION_MINOR_NUMBER.desc())
            .limit(1)
            .fetchOne { record -> record.into(VERSION).toDomain() }
    }

    override fun findOrderByVersionNumberDesc(projectId: ProjectId, versionMajorNumber: Int): ProjectVersion? {
        return dsl
            .selectFrom(VERSION)
            .where(VERSION.PROJECT_ID.eq(projectId.toLong()))
            .and(VERSION.VERSION_MAJOR_NUMBER.eq(versionMajorNumber))
            .orderBy(VERSION.VERSION_MAJOR_NUMBER.desc(), VERSION.VERSION_MINOR_NUMBER.desc())
            .limit(1)
            .fetchOne { record -> record.into(VERSION).toDomain() }
    }

    override fun findAll(projectId: ProjectId): List<ProjectVersion> {
        return dsl
            .selectFrom(VERSION)
            .where(VERSION.PROJECT_ID.eq(projectId.toLong()))
            .fetch { record -> record.into(VERSION).toDomain() }
    }

    override fun delete(projectVersion: ProjectVersion) {
        dsl.deleteFrom(VERSION)
            .where(VERSION.PROJECT_ID.eq(projectVersion.projectId.toLong()))
            .and(VERSION.VERSION_MAJOR_NUMBER.eq(projectVersion.major))
            .and(VERSION.VERSION_MINOR_NUMBER.eq(projectVersion.minor))
            .execute()
    }

    override fun deleteAll(projectId: ProjectId) {
        dsl
            .deleteFrom(VERSION)
            .where(VERSION.PROJECT_ID.eq(projectId.toLong()))
            .execute()
    }

    override fun deleteAll(
        projectId: ProjectId,
        majorNumber: Int
    ) {
        dsl.deleteFrom(VERSION)
            .where(VERSION.PROJECT_ID.eq(projectId.toLong()))
            .and(VERSION.VERSION_MAJOR_NUMBER.eq(majorNumber))
            .execute()
    }
}