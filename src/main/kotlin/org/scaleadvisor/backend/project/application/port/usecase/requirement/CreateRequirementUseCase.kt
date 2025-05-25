package org.scaleadvisor.backend.project.application.port.usecase.requirement

import org.scaleadvisor.backend.project.domain.Requirement
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface CreateRequirementUseCase {

    fun createAll(projectId: ProjectId, requirementList: List<Requirement>)

}