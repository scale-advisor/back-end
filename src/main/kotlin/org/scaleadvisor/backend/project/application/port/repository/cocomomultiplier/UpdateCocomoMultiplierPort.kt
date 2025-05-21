package org.scaleadvisor.backend.project.application.port.repository.cocomomultiplier

import org.scaleadvisor.backend.project.domain.CocomoMultiplier

fun interface UpdateCocomoMultiplierPort {
    fun update(cocomoMultiplier: CocomoMultiplier)
}