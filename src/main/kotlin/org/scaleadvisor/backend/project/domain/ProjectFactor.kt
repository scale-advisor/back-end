package org.scaleadvisor.backend.project.domain

import org.scaleadvisor.backend.project.domain.enum.CocomoType
import org.scaleadvisor.backend.project.domain.id.ProjectId

data class ProjectFactor(
    val projectId: ProjectId,
    var unitCost: Int?,
    var teamSize: Int?,
    var cocomoType: CocomoType?,
)