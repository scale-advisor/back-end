package org.scaleadvisor.backend.project.application.port.repository.requirement

import org.scaleadvisor.backend.project.domain.ProjectRequirement
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface CreateRequirementPort {

    fun createAll(
        projectId: ProjectId,
        projectVersion: ProjectVersion,
        projectRequirementList: List<ProjectRequirement>,
    )

}