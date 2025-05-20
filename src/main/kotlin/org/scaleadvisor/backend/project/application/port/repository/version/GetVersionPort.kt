package org.scaleadvisor.backend.project.application.port.repository.version

import org.scaleadvisor.backend.project.domain.Version
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface GetVersionPort {

    fun findAll(projectId: ProjectId): List<Version>

}