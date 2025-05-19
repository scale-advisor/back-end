package org.scaleadvisor.backend.project.application.port.usecase

import org.scaleadvisor.backend.project.domain.id.ProjectId

interface GetProjectVersionListUseCase {

    fun findAll(projectId: ProjectId): List<String>

}