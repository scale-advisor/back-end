package org.scaleadvisor.backend.project.controller.request.cocomomultiplier

import org.scaleadvisor.backend.project.domain.enum.CocomoLevel

data class UpdateCocomoMultiplierRequest(
    var rcpx: CocomoLevel,
    var ruse: CocomoLevel,
    var pdif: CocomoLevel,
    var pers: CocomoLevel,
    var sced: CocomoLevel,
    var fcil: CocomoLevel
)