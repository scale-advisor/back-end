package org.scaleadvisor.backend.project.domain

import ProjectVersionId
import org.scaleadvisor.backend.project.domain.enum.AdjustmentFactorType
import org.scaleadvisor.backend.project.domain.id.AdjustmentFactorId

data class AdjustmentFactor(
    val id: AdjustmentFactorId,
    val projectVersionId: ProjectVersionId,
    val type: AdjustmentFactorType,
    val level: Int,
) {
    val value: String get() = type.valueFor(level)
}