package org.scaleadvisor.backend.project.domain.id

import org.scaleadvisor.backend.project.domain.enum.CocomoLevel
import java.time.LocalDateTime

data class CocomoMultiplier(
    val cocomoMultiplierId: CocomoMultiplierId,
    val projectId: ProjectId,

    var rcpx: CocomoLevel,
    var ruse: CocomoLevel,
    var pdif: CocomoLevel,
    var pers: CocomoLevel,
    var sced: CocomoLevel,
    var fcil: CocomoLevel,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime?
){
    fun update(
        rcpx: CocomoLevel,
        ruse: CocomoLevel,
        pdif: CocomoLevel,
        pers: CocomoLevel,
        sced: CocomoLevel,
        fcil: CocomoLevel
    ){
        this.rcpx = rcpx
        this.ruse = ruse
        this.pdif = pdif
        this.pers = pers
        this.sced = sced
        this.fcil = fcil
        this.updatedAt = LocalDateTime.now()
    }
}
