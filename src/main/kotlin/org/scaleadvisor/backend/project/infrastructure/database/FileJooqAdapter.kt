package org.scaleadvisor.backend.project.infrastructure.database

import org.jooq.DSLContext
import org.jooq.generated.Tables.FILE
import org.jooq.generated.tables.records.FileRecord
import org.scaleadvisor.backend.project.application.port.repository.file.CreateFilePort
import org.scaleadvisor.backend.project.application.port.repository.file.DeleteFilePort
import org.scaleadvisor.backend.project.application.port.repository.file.GetFilePort
import org.scaleadvisor.backend.project.domain.File
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.enum.FileType
import org.scaleadvisor.backend.project.domain.id.FileId
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Repository

@Repository
private class FileJooqAdapter(
    private val dsl: DSLContext
) : CreateFilePort, GetFilePort, DeleteFilePort {

    private fun FileRecord.toDomain() = File(
        id = FileId.of(this.fileId),
        projectId = ProjectId.of(this.projectId),
        projectVersion = ProjectVersion.of(this.versionMajorNumber, this.versionMinorNumber),
        name = this.name,
        type = FileType.valueOf(this.type),
        uploaderId = this.uploaderId,
        path = this.path,
        extension = this.extension,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )

    override fun create(file: File) {
        dsl.insertInto(FILE)
            .set(FILE.FILE_ID, file.id.toLong())
            .set(FILE.PROJECT_ID, file.projectId.toLong())
            .set(FILE.VERSION_MAJOR_NUMBER, file.projectVersion.major)
            .set(FILE.VERSION_MINOR_NUMBER, file.projectVersion.minor)
            .set(FILE.NAME, file.name)
            .set(FILE.TYPE, file.type.name)
            .set(FILE.UPLOADER_ID, file.uploaderId)
            .set(FILE.PATH, file.path)
            .set(FILE.EXTENSION, file.extension)
            .set(FILE.CREATED_AT, file.createdAt)
            .set(FILE.UPDATED_AT, file.updatedAt)
            .execute()
    }

    override fun find(
        projectId: ProjectId,
        projectVersion: ProjectVersion
    ): File? {
        return dsl.selectFrom(FILE)
            .where(FILE.PROJECT_ID.eq(projectId.toLong()))
            .and(FILE.VERSION_MAJOR_NUMBER.eq(projectVersion.major))
            .and(FILE.VERSION_MINOR_NUMBER.eq(projectVersion.minor))
            .fetchOne { record -> record.into(FILE).toDomain() }
    }

    override fun delete(projectId: ProjectId, projectVersion: ProjectVersion) {
        dsl.deleteFrom(FILE)
            .where(FILE.PROJECT_ID.eq(projectId.toLong()))
            .and(FILE.VERSION_MAJOR_NUMBER.eq(projectVersion.major))
            .and(FILE.VERSION_MINOR_NUMBER.eq(projectVersion.minor))
            .execute()
    }

    override fun deleteAll(projectId: ProjectId) {
        dsl.deleteFrom(FILE)
            .where(FILE.PROJECT_ID.eq(projectId.toLong()))
            .execute()
    }

}