package org.scaleadvisor.backend.project.application.port.usecase.requirementcategory

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.RequirementCategory

interface GetRequirementCategoryUseCase {
    fun findAll(projectVersion: ProjectVersion): List<RequirementCategory>
}