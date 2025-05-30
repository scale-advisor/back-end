package org.scaleadvisor.backend.project.application.port.usecase.requirementcategory

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface DeleteRequirementCategoryUseCase {
    fun deleteAll(projectId: ProjectId)
    fun deleteAll(projectVersion: ProjectVersion)
}