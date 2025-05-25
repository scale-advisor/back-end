package org.scaleadvisor.backend.project.application.port.repository.requirement

import org.scaleadvisor.backend.project.domain.Requirement
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.vo.VersionNumber

fun interface CreateRequirementPort {

    fun createAll(
        projectId: ProjectId,
        versionNumber: VersionNumber,
        requirementList: List<Requirement>,
    )

}