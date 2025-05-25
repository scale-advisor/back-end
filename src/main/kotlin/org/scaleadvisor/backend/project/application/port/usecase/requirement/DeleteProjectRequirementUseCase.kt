package org.scaleadvisor.backend.project.application.port.usecase.requirement

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface DeleteProjectRequirementUseCase {

    fun deleteAll(projectId: ProjectId)

    fun deleteAll(projectId: ProjectId, projectVersion: ProjectVersion)

}