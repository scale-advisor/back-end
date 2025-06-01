package org.scaleadvisor.backend.project.application.port.repository.requirement

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.id.RequirementId

interface DeleteRequirementPort {

    fun deleteAll(projectId: ProjectId)
    fun deleteAll(projectId: ProjectId, versionMajorNumber: Int)
    fun deleteAll(projectVersion: ProjectVersion)
    fun deleteAll(requirementIdList: List<RequirementId>)

}