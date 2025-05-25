package org.scaleadvisor.backend.project.infrastructure.database

import org.jooq.DSLContext
import org.jooq.generated.Tables.FILE
import org.jooq.generated.tables.records.FileRecord
import org.scaleadvisor.backend.project.application.port.repository.file.CreateFilePort
import org.scaleadvisor.backend.project.application.port.repository.file.DeleteFilePort
import org.scaleadvisor.backend.project.application.port.repository.file.GetFilePort
import org.scaleadvisor.backend.project.domain.File
import org.scaleadvisor.backend.project.domain.VersionNumber
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
        versionNumber = VersionNumber.of(this.majorNumber, this.minorNumber),
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
            .set(FILE.MAJOR_NUMBER, file.versionNumber.major)
            .set(FILE.MINOR_NUMBER, file.versionNumber.minor)
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
        versionNumber: VersionNumber
    ): File? {
        return dsl.selectFrom(FILE)
            .where(FILE.PROJECT_ID.eq(projectId.toLong()))
            .and(FILE.MAJOR_NUMBER.eq(versionNumber.major))
            .and(FILE.MINOR_NUMBER.eq(versionNumber.minor))
            .fetchOne { record -> record.into(FILE).toDomain() }
    }

    override fun deleteAll(projectId: ProjectId) {
        dsl.deleteFrom(FILE)
            .where(FILE.PROJECT_ID.eq(projectId.toLong()))
            .execute()
    }

}