package org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor

import ProjectVersionId
import org.scaleadvisor.backend.project.domain.AdjustmentFactor

fun interface GetAdjustmentFactorPort {
    fun findAll(projectVersionId: ProjectVersionId): List<AdjustmentFactor>
}