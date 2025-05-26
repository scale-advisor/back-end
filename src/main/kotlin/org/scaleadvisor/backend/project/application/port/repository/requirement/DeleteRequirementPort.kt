package org.scaleadvisor.backend.project.application.port.repository.requirement

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface DeleteRequirementPort {

    fun deleteAll(projectId: ProjectId)

    fun deleteAll(projectId: ProjectId, versionMajorNumber: Int)

    fun deleteAll(projectVersion: ProjectVersion)

}