package org.scaleadvisor.backend.project.application.port.repository.file

import org.scaleadvisor.backend.project.domain.File
import org.scaleadvisor.backend.project.domain.ProjectVersion

fun interface GetFilePort {

    fun find(projectVersion: ProjectVersion): File?

}
