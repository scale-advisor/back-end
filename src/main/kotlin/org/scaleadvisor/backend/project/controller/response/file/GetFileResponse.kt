package org.scaleadvisor.backend.project.controller.response.file

import org.scaleadvisor.backend.project.domain.File
import org.scaleadvisor.backend.project.domain.enum.FileType
import java.time.LocalDateTime

data class GetFileResponse (
    val projectId: String,
    val versionNumber: String,
    val name: String,
    val type: FileType,
    val uploaderId: String,
    val extension: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        @JvmStatic
        fun from(file: File): GetFileResponse {
            return GetFileResponse(
                projectId = file.projectId.toString(),
                versionNumber = file.versionNumber.toString(),
                name = file.name,
                type = file.type,
                uploaderId = file.uploaderId.toString(),
                extension = file.extension,
                createdAt = file.createdAt,
                updatedAt = file.updatedAt
            )
        }
    }
}