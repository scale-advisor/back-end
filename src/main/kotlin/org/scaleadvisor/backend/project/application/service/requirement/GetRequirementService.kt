package org.scaleadvisor.backend.project.application.service.requirement

import org.scaleadvisor.backend.project.application.port.repository.requirement.GetRequirementPort
import org.scaleadvisor.backend.project.application.port.usecase.requirement.GetRequirementUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.Requirement
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetRequirementService(
    private val getRequirementPort: GetRequirementPort,
) : GetRequirementUseCase {
    override fun findAll(projectVersion: ProjectVersion): List<Requirement> {
        return getRequirementPort.findAll(projectVersion)
    }

    override fun findAll(
        projectVersion: ProjectVersion,
        requirementNumberPrefixList: List<String>
    ): List<Requirement> {
        return getRequirementPort.findAll(
            projectVersion,
            requirementNumberPrefixList
        )
    }


}