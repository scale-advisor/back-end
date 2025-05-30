package org.scaleadvisor.backend.project.application.port.repository.requirementcategory

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.RequirementCategory

interface GetRequirementCategoryPort {
    fun findAll(projectVersion: ProjectVersion): List<RequirementCategory>
}