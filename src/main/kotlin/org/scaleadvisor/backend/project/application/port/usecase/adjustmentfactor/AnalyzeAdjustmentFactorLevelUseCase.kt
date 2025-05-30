package org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor

import org.scaleadvisor.backend.project.domain.AdjustmentFactor
import org.scaleadvisor.backend.project.domain.ProjectVersion

interface AnalyzeAdjustmentFactorLevelUseCase {
    operator fun invoke(projectVersion: ProjectVersion): List<AdjustmentFactor>
}