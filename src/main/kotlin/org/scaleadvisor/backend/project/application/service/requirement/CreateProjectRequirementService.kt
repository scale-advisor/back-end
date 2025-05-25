package org.scaleadvisor.backend.project.application.service.requirement

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.requirement.CreateRequirementPort
import org.scaleadvisor.backend.project.application.port.usecase.requirement.CreateProjectRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.GetProjectVersionUseCase
import org.scaleadvisor.backend.project.domain.ProjectRequirement
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service

@Service
private class CreateProjectRequirementService(
    private val getProjectVersionUseCase: GetProjectVersionUseCase,
    private val createRequirementPort: CreateRequirementPort,
) : CreateProjectRequirementUseCase {
    override fun createAll(
        projectId: ProjectId,
        projectRequirementList: List<ProjectRequirement>
    ) {
        val projectVersion: ProjectVersion = getProjectVersionUseCase.findLatest(projectId)
            ?: throw NotFoundException("Project version not found")

        createRequirementPort.createAll(projectId, projectVersion, projectRequirementList)
    }
}