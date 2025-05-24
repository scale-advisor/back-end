package org.scaleadvisor.backend.project.controller.request.cocomoscalefactor

import org.scaleadvisor.backend.project.domain.enum.CocomoLevel

data class UpdateCocomoScaleFactorRequest(
    var prec: CocomoLevel,
    var flex: CocomoLevel,
    var resl: CocomoLevel,
    var team: CocomoLevel,
    var pmat: CocomoLevel
)