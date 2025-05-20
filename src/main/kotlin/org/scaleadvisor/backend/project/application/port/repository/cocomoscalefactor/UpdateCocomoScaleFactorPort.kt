package org.scaleadvisor.backend.project.application.port.repository.cocomoscalefactor

import org.scaleadvisor.backend.project.domain.CocomoScaleFactor

fun interface UpdateCocomoScaleFactorPort {
    fun update(cocomoScaleFactor: CocomoScaleFactor)
}