package org.scaleadvisor.backend.project.domain

import org.scaleadvisor.backend.project.domain.enum.CocomoLevel
import org.scaleadvisor.backend.project.domain.id.CocomoScaleFactorId
import org.scaleadvisor.backend.project.domain.id.ProjectId
import java.time.LocalDateTime

data class CocomoScaleFactor(
    val cocomoScaleFactorId: CocomoScaleFactorId,
    val projectId: ProjectId,

    var prec: CocomoLevel,
    var flex: CocomoLevel,
    var resl: CocomoLevel,
    var team: CocomoLevel,
    var pmat: CocomoLevel,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime?
){
    fun update(
        prec: CocomoLevel,
        flex: CocomoLevel,
        resl: CocomoLevel,
        team: CocomoLevel,
        pmat: CocomoLevel
    ) {
        this.prec = prec
        this.flex = flex
        this.resl = resl
        this.team = team
        this.pmat = pmat
        this.updatedAt = LocalDateTime.now()
    }
}