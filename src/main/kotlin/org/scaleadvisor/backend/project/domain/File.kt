package org.scaleadvisor.backend.project.domain

import org.scaleadvisor.backend.project.domain.enum.FileType
import org.scaleadvisor.backend.project.domain.id.FileId
import org.scaleadvisor.backend.project.domain.id.ProjectId
import java.time.LocalDateTime

data class File (
    val id: FileId,
    val projectId: ProjectId,
    val versionNumber: String,
    var name: String,
    var type: FileType,
    var uploaderId: Long,
    var path: String,
    var extension: String,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
)