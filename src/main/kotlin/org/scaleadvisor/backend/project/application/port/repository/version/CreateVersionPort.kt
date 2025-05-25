package org.scaleadvisor.backend.project.application.port.repository.version

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface CreateVersionPort {

    fun create(projectId: ProjectId, projectVersion: ProjectVersion)

}