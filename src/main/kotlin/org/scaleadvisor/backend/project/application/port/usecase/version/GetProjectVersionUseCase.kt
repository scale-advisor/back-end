package org.scaleadvisor.backend.project.application.port.usecase.version

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface GetProjectVersionUseCase {

    fun findLatest(projectId: ProjectId): ProjectVersion?

    fun findLatestMinor(projectVersion: ProjectVersion): Int

    fun findAll(projectId: ProjectId): List<ProjectVersion>

}