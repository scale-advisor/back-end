package org.scaleadvisor.backend.project.api.request

import org.scaleadvisor.backend.project.domain.enum.CocomoLevel

data class CreateCocomoScaleFactorRequest(
    var prec: CocomoLevel,
    var flex: CocomoLevel,
    var resl: CocomoLevel,
    var team: CocomoLevel,
    var pmat: CocomoLevel
)
