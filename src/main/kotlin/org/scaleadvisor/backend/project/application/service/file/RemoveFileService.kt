package org.scaleadvisor.backend.project.application.service.file

import org.scaleadvisor.backend.project.application.port.repository.file.RemoveFilePort
import org.scaleadvisor.backend.project.application.port.usecase.file.RemoveFileUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service

@Service
private class RemoveFileService(
    private val removeFilePort: RemoveFilePort
) : RemoveFileUseCase {
    override fun remove(projectId: ProjectId, projectVersion: ProjectVersion) {
        removeFilePort.remove(projectId, projectVersion)
    }

    override fun removeAll(projectId: ProjectId) {
        removeFilePort.removeAll(projectId)
    }

}