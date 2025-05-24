package org.scaleadvisor.backend.project.application.service.project

import org.scaleadvisor.backend.project.application.port.repository.project.GetProjectPort
import org.scaleadvisor.backend.project.application.port.usecase.project.GetProjectUseCase
import org.scaleadvisor.backend.project.domain.Project
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
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