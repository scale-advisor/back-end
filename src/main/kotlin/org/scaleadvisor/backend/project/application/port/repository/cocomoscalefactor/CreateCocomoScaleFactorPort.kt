package org.scaleadvisor.backend.project.application.port.repository.cocomoscalefactor

import org.scaleadvisor.backend.project.domain.CocomoScaleFactor

fun interface CreateCocomoScaleFactorPort {
    fun create(cocomoScaleFactor: CocomoScaleFactor)
}