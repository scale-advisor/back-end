package org.scaleadvisor.backend.project.application.port.repository.file

import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.multipart.MultipartFile

fun interface UploadFilePort {
    fun upload(
        projectId: ProjectId,
        file: MultipartFile,
        path: String
    ): String
}