package org.scaleadvisor.backend.project.application.port.usecase.requirement

import org.scaleadvisor.backend.project.domain.ProjectRequirement
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface CreateProjectRequirementUseCase {

    fun createAll(projectId: ProjectId, projectRequirementList: List<ProjectRequirement>)

}