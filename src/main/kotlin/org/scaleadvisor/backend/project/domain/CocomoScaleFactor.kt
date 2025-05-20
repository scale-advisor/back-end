package org.scaleadvisor.backend.project.domain

import org.scaleadvisor.backend.project.domain.enum.CocomoScaleFactorLevel
import org.scaleadvisor.backend.project.domain.id.CocomoScaleFactorId
import org.scaleadvisor.backend.project.domain.id.ProjectId
import java.time.LocalDateTime

data class CocomoScaleFactor(
    val cocomoScaleFactorId: CocomoScaleFactorId,
    val projectId: ProjectId,

    val prec: CocomoScaleFactorLevel,
    val flex: CocomoScaleFactorLevel,
    val resl: CocomoScaleFactorLevel,
    val team: CocomoScaleFactorLevel,
    val pmat: CocomoScaleFactorLevel,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime?
)
