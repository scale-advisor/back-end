package org.scaleadvisor.backend.project.application.service.requirement

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.requirement.CreateRequirementPort
import org.scaleadvisor.backend.project.application.port.usecase.requirement.CreateRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.GetVersionUseCase
import org.scaleadvisor.backend.project.domain.Requirement
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.vo.VersionNumber
import org.springframework.stereotype.Service

@Service
private class CreateRequirementService(
    private val getVersionUseCase: GetVersionUseCase,
    private val createRequirementPort: CreateRequirementPort,
) : CreateRequirementUseCase {
    override fun createAll(
        projectId: ProjectId,
        requirementList: List<Requirement>
    ) {
        val versionNumber: VersionNumber = getVersionUseCase.findLatest(projectId)
            ?: throw NotFoundException("Project version not found")

        createRequirementPort.createAll(projectId, versionNumber, requirementList)
    }
}