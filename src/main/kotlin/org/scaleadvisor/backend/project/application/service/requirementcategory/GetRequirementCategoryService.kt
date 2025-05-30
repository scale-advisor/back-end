package org.scaleadvisor.backend.project.application.service.requirementcategory

import org.scaleadvisor.backend.project.application.port.repository.requirementcategory.GetRequirementCategoryPort
import org.scaleadvisor.backend.project.application.port.usecase.requirementcategory.GetRequirementCategoryUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.RequirementCategory
import org.scaleadvisor.backend.project.domain.enum.RequirementCategoryName
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetRequirementCategoryService(
    private val getRequirementCategoryPort: GetRequirementCategoryPort,
): GetRequirementCategoryUseCase {
    override fun findAll(projectVersion: ProjectVersion): List<RequirementCategory> {
        return getRequirementCategoryPort.findAll(projectVersion)
    }

    override fun findAll(
        projectVersion: ProjectVersion,
        categoryName: RequirementCategoryName
    ): List<RequirementCategory> {
        return getRequirementCategoryPort.findAll(projectVersion, categoryName)
    }
}