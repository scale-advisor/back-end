package org.scaleadvisor.backend.project.application.service

import org.scaleadvisor.backend.project.application.port.repository.GetProjectPort
import org.scaleadvisor.backend.project.application.port.usecase.GetProjectUseCase
import org.scaleadvisor.backend.project.domain.Project
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service

@Service
private class GetProjectService(
    private val getProjectPort: GetProjectPort
) : GetProjectUseCase {
    override fun find(projectId: ProjectId): Project? {
        return getProjectPort.find(projectId)
    }

    override fun findAll(userId: Long): List<Project> {
        return getProjectPort.findAll(userId)
    }
}