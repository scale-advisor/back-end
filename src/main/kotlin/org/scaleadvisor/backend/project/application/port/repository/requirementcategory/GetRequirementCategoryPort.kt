package org.scaleadvisor.backend.project.application.port.repository.requirementcategory

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.RequirementCategory
import org.scaleadvisor.backend.project.domain.enum.RequirementCategoryName

interface GetRequirementCategoryPort {
    fun findAll(projectVersion: ProjectVersion): List<RequirementCategory>
    fun findAll(
        projectVersion: ProjectVersion,
        categoryName: RequirementCategoryName
    ): List<RequirementCategory>

}