package org.scaleadvisor.backend.project.application.port.repository.project

import org.scaleadvisor.backend.project.domain.Project
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface GetProjectPort {

    fun find(projectId: ProjectId): Project?

    fun findAll(userId: Long): List<Project>

}