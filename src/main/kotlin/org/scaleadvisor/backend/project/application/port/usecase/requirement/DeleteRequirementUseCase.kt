package org.scaleadvisor.backend.project.application.port.usecase.requirement

import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.vo.VersionNumber

interface DeleteRequirementUseCase {

    fun deleteAll(projectId: ProjectId)

    fun deleteAll(projectId: ProjectId, versionNumber: VersionNumber)

}