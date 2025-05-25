package org.scaleadvisor.backend.project.application.service.requirement

import org.scaleadvisor.backend.project.application.port.repository.requirement.DeleteRequirementPort
import org.scaleadvisor.backend.project.application.port.usecase.requirement.DeleteProjectRequirementUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteProjectRequirementService(
    private val deleteRequirementPort: DeleteRequirementPort,
) : DeleteProjectRequirementUseCase {

    override fun deleteAll(projectId: ProjectId) {
        deleteRequirementPort.deleteAll(projectId)
    }

    override fun deleteAll(
        projectId: ProjectId,
        projectVersion: ProjectVersion
    ) {
        if (projectVersion.minor == 0) {
            deleteRequirementPort.deleteAll(
                projectId = projectId,
                versionMajorNumber = projectVersion.major
            )
        } else {
            deleteRequirementPort.deleteAll(
                projectId = projectId,
                projectVersion = projectVersion
            )
        }
    }

}