package org.scaleadvisor.backend.project.application.service.version

import org.scaleadvisor.backend.project.application.port.repository.version.DeleteProjectVersionPort
import org.scaleadvisor.backend.project.application.port.usecase.file.DeleteFileUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirement.DeleteRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.DeleteProjectVersionUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteProjectVersionService(
    private val deleteRequirementUseCase: DeleteRequirementUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
    private val deleteProjectVersionPort: DeleteProjectVersionPort,
) : DeleteProjectVersionUseCase {

    override fun delete(
        projectId: ProjectId,
        versionNumber: String
    ) {
        val projectVersion = ProjectVersion.of(projectId, versionNumber)

        deleteRequirementUseCase.deleteAll(projectVersion)
        if (projectVersion.minor == 0) {
            deleteFileUseCase.delete(projectVersion)
            deleteProjectVersionPort.deleteAll(projectId, projectVersion.major)
        } else {
            deleteProjectVersionPort.delete(projectVersion)
        }
    }

    override fun deleteAll(projectId: ProjectId) {
        deleteRequirementUseCase.deleteAll(projectId)
        deleteFileUseCase.deleteAll(projectId)
        deleteProjectVersionPort.deleteAll(projectId)
    }
}