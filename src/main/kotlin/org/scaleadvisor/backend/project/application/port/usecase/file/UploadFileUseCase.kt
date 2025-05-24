package org.scaleadvisor.backend.project.application.port.usecase.file

import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.multipart.MultipartFile

fun interface UploadFileUseCase {
    data class Command(
        val projectId: ProjectId,
        val file: MultipartFile,
        val path: String
    )

    fun upload(command: Command)

}