package org.scaleadvisor.backend.project.api.request

import org.scaleadvisor.backend.project.domain.enum.CocomoScaleFactorLevel

data class UpdateCocomoScaleFactorRequest(
    var prec: CocomoScaleFactorLevel,
    var flex: CocomoScaleFactorLevel,
    var resl: CocomoScaleFactorLevel,
    var team: CocomoScaleFactorLevel,
    var pmat: CocomoScaleFactorLevel
)
