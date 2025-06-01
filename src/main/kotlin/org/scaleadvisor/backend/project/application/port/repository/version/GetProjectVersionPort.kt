package org.scaleadvisor.backend.project.application.port.repository.version

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface GetProjectVersionPort {

    fun findOrderByVersionNumberDesc(projectId: ProjectId): ProjectVersion?

    fun findOrderByVersionNumberDesc(projectId: ProjectId, versionMajorNumber: Int): ProjectVersion?

    fun findAll(projectId: ProjectId): List<ProjectVersion>

}