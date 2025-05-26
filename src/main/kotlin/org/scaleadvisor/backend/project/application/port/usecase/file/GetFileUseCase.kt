package org.scaleadvisor.backend.project.application.port.usecase.file

import org.scaleadvisor.backend.project.domain.File
import org.scaleadvisor.backend.project.domain.ProjectVersion

fun interface GetFileUseCase {

    fun find(projectVersion: ProjectVersion): File

}