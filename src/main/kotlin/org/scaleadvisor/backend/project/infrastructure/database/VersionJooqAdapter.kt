package org.scaleadvisor.backend.project.infrastructure.database

import org.jooq.DSLContext
import org.jooq.generated.Tables.VERSION
import org.jooq.generated.tables.records.VersionRecord
import org.scaleadvisor.backend.project.application.port.repository.version.CreateVersionPort
import org.scaleadvisor.backend.project.application.port.repository.version.DeleteVersionPort
import org.scaleadvisor.backend.project.application.port.repository.version.GetVersionPort
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
private class VersionJooqAdapter(
    private val dsl: DSLContext
) : CreateVersionPort, GetVersionPort, DeleteVersionPort {

    private fun VersionRecord.toDomain() = ProjectVersion.of(
        major = this.versionMajorNumber,
        minor = this.versionMinorNumber
    )

    override fun create(projectId: ProjectId, projectVersion: ProjectVersion) {
        dsl
            .insertInto(VERSION)
            .set(VERSION.PROJECT_ID, projectId.toLong())
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

    override fun findAll(projectId: ProjectId): List<ProjectVersion> {
        return dsl
            .selectFrom(VERSION)
            .where(VERSION.PROJECT_ID.eq(projectId.toLong()))
            .fetch { record -> record.into(VERSION).toDomain() }
    }

    override fun delete(projectId: ProjectId, projectVersion: ProjectVersion) {
        dsl.deleteFrom(VERSION)
            .where(VERSION.PROJECT_ID.eq(projectId.toLong()))
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