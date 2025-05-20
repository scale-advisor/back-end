package org.scaleadvisor.backend.project.infrastructure

import org.jooq.DSLContext
import org.jooq.generated.Tables.VERSION
import org.jooq.generated.tables.records.VersionRecord
import org.scaleadvisor.backend.project.application.port.repository.CreateVersionPort
import org.scaleadvisor.backend.project.application.port.repository.GetVersionPort
import org.scaleadvisor.backend.project.domain.Version
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
private class VersionJooqAdapter(
    private val dsl: DSLContext
) : CreateVersionPort, GetVersionPort {

    private fun VersionRecord.toDomain() = Version(
        projectId = ProjectId.of(this.projectId),
        versionNumber = this.versionNumber
    )

    override fun create(version: Version) {
        dsl
            .insertInto(VERSION)
            .set(VERSION.PROJECT_ID, version.projectId.toLong())
            .set(VERSION.VERSION_NUMBER, version.versionNumber)
            .set(VERSION.CREATED_AT, LocalDateTime.now())
            .execute()
    }

    override fun findAll(projectId: ProjectId): List<Version> {
        return dsl
            .selectFrom(VERSION)
            .where(VERSION.PROJECT_ID.eq(projectId.toLong()))
            .fetch { record -> record.into(VERSION).toDomain() }
    }
}