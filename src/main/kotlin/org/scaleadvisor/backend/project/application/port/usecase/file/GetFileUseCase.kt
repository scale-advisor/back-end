package org.scaleadvisor.backend.project.application.port.usecase.file

import org.scaleadvisor.backend.project.domain.File
import org.scaleadvisor.backend.project.domain.VersionNumber
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface GetFileUseCase {

    data class Command(
        val projectId: ProjectId,
        val versionNumber: VersionNumber,
    )

    fun find(command: Command): File

}