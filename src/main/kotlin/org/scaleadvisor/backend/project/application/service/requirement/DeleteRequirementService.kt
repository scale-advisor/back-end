package org.scaleadvisor.backend.project.application.service.requirement

import org.scaleadvisor.backend.project.application.port.repository.requirement.DeleteRequirementPort
import org.scaleadvisor.backend.project.application.port.usecase.requirement.DeleteRequirementUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.id.RequirementId
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
        versionMajorNumber: Int
    ) {
        deleteRequirementPort.deleteAll(
            projectId,
            versionMajorNumber
        )
    }

    override fun deleteAll(projectVersion: ProjectVersion) {
        deleteRequirementPort.deleteAll(projectVersion)
    }

    override fun deleteAll(requirementIdList: List<RequirementId>) {
        deleteRequirementPort.deleteAll(requirementIdList)
    }

}