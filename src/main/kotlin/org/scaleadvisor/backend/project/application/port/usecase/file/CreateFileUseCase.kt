package org.scaleadvisor.backend.project.application.port.usecase.file

import org.scaleadvisor.backend.project.domain.enum.FileType
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.multipart.MultipartFile

fun interface CreateFileUseCase {
    data class Command(
        val projectId: ProjectId,
        val name: String,
        val type: FileType,
        val uploaderId: Long,
        val file: MultipartFile
    )

    fun create(command: Command)

}