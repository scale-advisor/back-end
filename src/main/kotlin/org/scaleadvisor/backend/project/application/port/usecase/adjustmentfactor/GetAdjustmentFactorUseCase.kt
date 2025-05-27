package org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor

import org.scaleadvisor.backend.project.domain.AdjustmentFactor
import org.scaleadvisor.backend.project.domain.ProjectVersion

fun interface GetAdjustmentFactorUseCase {
    fun findAll(projectVersion: ProjectVersion): List<AdjustmentFactor>
}