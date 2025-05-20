package org.scaleadvisor.backend.project.domain

import org.scaleadvisor.backend.project.domain.enum.CocomoScaleFactorLevel
import org.scaleadvisor.backend.project.domain.id.CocomoScaleFactorId
import org.scaleadvisor.backend.project.domain.id.ProjectId
import java.time.LocalDateTime

data class CocomoScaleFactor(
    val cocomoScaleFactorId: CocomoScaleFactorId,
    val projectId: ProjectId,

    var prec: CocomoScaleFactorLevel,
    var flex: CocomoScaleFactorLevel,
    var resl: CocomoScaleFactorLevel,
    var team: CocomoScaleFactorLevel,
    var pmat: CocomoScaleFactorLevel,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime?
){
    fun update(
        prec: CocomoScaleFactorLevel,
        flex: CocomoScaleFactorLevel,
        resl: CocomoScaleFactorLevel,
        team: CocomoScaleFactorLevel,
        pmat: CocomoScaleFactorLevel
    ) {
        this.prec = prec
        this.flex = flex
        this.resl = resl
        this.team = team
        this.pmat = pmat
        this.updatedAt = LocalDateTime.now()
    }
}