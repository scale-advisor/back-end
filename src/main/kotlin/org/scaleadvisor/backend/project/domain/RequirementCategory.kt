package org.scaleadvisor.backend.project.domain

import ProjectVersionId
import org.scaleadvisor.backend.project.domain.enum.RequirementCategoryName
import org.scaleadvisor.backend.project.domain.id.RequirementCategoryId

data class RequirementCategory(
    val id: RequirementCategoryId,
    val projectVersionId: ProjectVersionId,
    val name: RequirementCategoryName,
    val prefix: String
)