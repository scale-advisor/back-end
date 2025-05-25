package org.scaleadvisor.backend.project.application.service.version

import org.scaleadvisor.backend.project.application.port.repository.version.DeleteVersionPort
import org.scaleadvisor.backend.project.application.port.usecase.file.DeleteFileUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirement.DeleteRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.DeleteVersionUseCase
import org.scaleadvisor.backend.project.domain.Version
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.vo.VersionNumber
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteVersionService(
    private val deleteRequirementUseCase: DeleteRequirementUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
    private val deleteVersionPort: DeleteVersionPort,
) : DeleteVersionUseCase {

    override fun delete(
        projectId: ProjectId,
        versionNumber: String
    ) {
        val version = Version(projectId, versionNumber)
        deleteRequirementUseCase.deleteAll(version.projectId, version.versionNumber)
        if (version.versionNumber.minor == 0) {
            deleteFileUseCase.delete(Version(projectId, VersionNumber.of(versionNumber)))
            deleteVersionPort.deleteAll(projectId, version.versionNumber.major)
        } else {
            deleteVersionPort.delete(version)
        }
    }

    override fun deleteAll(projectId: ProjectId) {
        deleteRequirementUseCase.deleteAll(projectId)
        deleteFileUseCase.deleteAll(projectId)
        deleteVersionPort.deleteAll(projectId)
    }
}