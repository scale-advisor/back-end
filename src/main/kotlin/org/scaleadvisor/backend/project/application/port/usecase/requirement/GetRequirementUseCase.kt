package org.scaleadvisor.backend.project.application.port.usecase.requirement

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.Requirement

interface GetRequirementUseCase {

    fun findAll(projectVersion: ProjectVersion): List<Requirement>

}