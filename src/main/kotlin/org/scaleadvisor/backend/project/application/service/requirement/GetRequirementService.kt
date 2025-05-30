package org.scaleadvisor.backend.project.application.service.requirement

import org.scaleadvisor.backend.project.application.port.repository.requirement.GetRequirementPort
import org.scaleadvisor.backend.project.application.port.usecase.requirement.GetRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirementcategory.GetRequirementCategoryUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.Requirement
import org.scaleadvisor.backend.project.domain.RequirementCategory
import org.scaleadvisor.backend.project.domain.enum.RequirementCategoryName
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetRequirementService(
    private val getRequirementCategoryUseCase: GetRequirementCategoryUseCase,
    private val getRequirementPort: GetRequirementPort,
) : GetRequirementUseCase {
    override fun findAllFunctionRequirement(projectVersion: ProjectVersion): List<Requirement> {
        val requirementCategoryList: List<RequirementCategory> = getRequirementCategoryUseCase.findAll(
            projectVersion,
            RequirementCategoryName.FUNCTION
        )

        return getRequirementPort.findAll(
            projectVersion,
            requirementCategoryList.map { requirementCategory -> requirementCategory.prefix }
        )
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

    override fun findAllId(projectId: ProjectId): List<RequirementId> {
        return getRequirementPort.findAllId(projectId)
    }

    override fun findAllId(
        projectId: ProjectId,
        versionMajorNumber: Int
    ): List<RequirementId> {
        return getRequirementPort.findAllId(
            projectId,
            versionMajorNumber
        )
    }

    override fun findAllId(projectVersion: ProjectVersion): List<RequirementId> {
        return getRequirementPort.findAllId(projectVersion)
    }

}