package org.scaleadvisor.backend.project.application.port.usecase.version

import org.scaleadvisor.backend.project.domain.id.ProjectId

interface GetVersionUseCase {

    fun findLatest(projectId: ProjectId): String

    fun findAll(projectId: ProjectId): List<String>

}