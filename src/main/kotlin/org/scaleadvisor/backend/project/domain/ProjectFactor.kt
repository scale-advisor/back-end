package org.scaleadvisor.backend.project.domain

import org.scaleadvisor.backend.project.domain.enum.CocomoType
import org.scaleadvisor.backend.project.domain.id.ProjectFactorId
import org.scaleadvisor.backend.project.domain.id.ProjectId
import java.time.LocalDateTime

data class ProjectFactor(
    val id: ProjectFactorId,
    var projectId: ProjectId,
    var unitCost: Int,
    var teamSize: Int,
    var cocomoType: CocomoType,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime?
)