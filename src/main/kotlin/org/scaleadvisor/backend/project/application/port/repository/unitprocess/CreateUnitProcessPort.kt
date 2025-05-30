package org.scaleadvisor.backend.project.application.port.repository.unitprocess

import org.scaleadvisor.backend.project.domain.UnitProcess

interface CreateUnitProcessPort {
    fun createAll(unitProcessList: List<UnitProcess>)
}