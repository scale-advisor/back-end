package org.scaleadvisor.backend.project.application.service.requirement

import org.scaleadvisor.backend.project.application.port.repository.requirement.DeleteRequirementPort
import org.scaleadvisor.backend.project.application.port.usecase.requirement.DeleteRequirementUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.vo.VersionNumber
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteRequirementService(
    private val deleteRequirementPort: DeleteRequirementPort,
) : DeleteRequirementUseCase {

    override fun deleteAll(projectId: ProjectId) {
        deleteRequirementPort.deleteAll(projectId)
    }

    override fun deleteAll(
        projectId: ProjectId,
        versionNumber: VersionNumber
    ) {
        if (versionNumber.minor == 0) {
            deleteRequirementPort.deleteAll(
                projectId = projectId,
                versionMajorNumber = versionNumber.major
            )
        } else {
            deleteRequirementPort.deleteAll(
                projectId = projectId,
                versionNumber = versionNumber
            )
        }
    }

}