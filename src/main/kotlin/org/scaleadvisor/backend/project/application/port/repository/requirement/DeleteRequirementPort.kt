package org.scaleadvisor.backend.project.application.port.repository.requirement

import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.vo.VersionNumber

interface DeleteRequirementPort {

    fun deleteAll(projectId: ProjectId)

    fun deleteAll(projectId: ProjectId, versionMajorNumber: Int)

    fun deleteAll(projectId: ProjectId, versionNumber: VersionNumber)

}