package org.scaleadvisor.backend.project.infrastructure.database

import org.jooq.DSLContext
import org.jooq.generated.Tables.FILE
import org.scaleadvisor.backend.project.application.port.repository.file.CreateFilePort
import org.scaleadvisor.backend.project.domain.File
import org.springframework.stereotype.Repository

@Repository
private class FileJooqAdapter(
    private val dsl: DSLContext
) : CreateFilePort {

    override fun create(file: File) {
        dsl.insertInto(FILE)
            .set(FILE.FILE_ID, file.id.toLong())
            .set(FILE.PROJECT_ID, file.projectId.toLong())
            .set(FILE.VERSION_NUMBER, file.versionNumber)
            .set(FILE.NAME, file.name)
            .set(FILE.TYPE, file.type.name)
            .set(FILE.UPLOADER_ID, file.uploaderId)
            .set(FILE.PATH, file.path)
            .set(FILE.EXTENSION, file.extension)
            .set(FILE.CREATED_AT, file.createdAt)
            .set(FILE.UPDATED_AT, file.updatedAt)
            .execute()
    }

}