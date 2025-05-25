package org.scaleadvisor.backend.project.application.port.repository.file

import org.scaleadvisor.backend.project.domain.File
import org.scaleadvisor.backend.project.domain.vo.VersionNumber
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface GetFilePort {

    fun find(projectId: ProjectId, versionNumber: VersionNumber): File?

}
