package org.scaleadvisor.backend.project.application.service.version

import org.scaleadvisor.backend.project.application.port.repository.version.GetVersionPort
import org.scaleadvisor.backend.project.application.port.usecase.version.GetProjectVersionListUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service

@Service
private class GetProjectVersionListService(
    private val getVersionPort: GetVersionPort
) : GetProjectVersionListUseCase {
    override fun findAll(projectId: ProjectId): List<String> {
        return getVersionPort.findAll(projectId).map { it.versionNumber }
    }
}