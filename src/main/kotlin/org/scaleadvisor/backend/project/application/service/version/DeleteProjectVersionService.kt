package org.scaleadvisor.backend.project.application.service.version

import org.scaleadvisor.backend.project.application.port.repository.version.DeleteVersionPort
import org.scaleadvisor.backend.project.application.port.usecase.file.DeleteFileUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirement.DeleteProjectRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.DeleteProjectVersionUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteProjectVersionService(
    private val deleteProjectRequirementUseCase: DeleteProjectRequirementUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
    private val deleteVersionPort: DeleteVersionPort,
) : DeleteProjectVersionUseCase {

    override fun delete(
        projectId: ProjectId,
        versionNumber: String
    ) {
        val projectVersion = ProjectVersion.of(versionNumber)
        deleteProjectRequirementUseCase.deleteAll(projectId, projectVersion)
        if (projectVersion.minor == 0) {
            deleteFileUseCase.delete(projectId, projectVersion)
            deleteVersionPort.deleteAll(projectId, projectVersion.major)
        } else {
            deleteVersionPort.delete(projectId, projectVersion)
        }
    }

    override fun deleteAll(projectId: ProjectId) {
        deleteProjectRequirementUseCase.deleteAll(projectId)
        deleteFileUseCase.deleteAll(projectId)
        deleteVersionPort.deleteAll(projectId)
    }
}