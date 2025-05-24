package org.scaleadvisor.backend.project.application.port.usecase.file

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DownloadFileUseCase {
    data class Command(
        val projectId: ProjectId,
        val path: String
    )

    fun download(command: Command): ByteArray

}