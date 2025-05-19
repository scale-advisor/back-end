package org.scaleadvisor.backend.project.application.port.usecase

import org.scaleadvisor.backend.project.domain.Project
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface GetProjectUseCase {

    fun find(projectId: ProjectId): Project?

    fun findAll(userId: Long): List<Project>

}