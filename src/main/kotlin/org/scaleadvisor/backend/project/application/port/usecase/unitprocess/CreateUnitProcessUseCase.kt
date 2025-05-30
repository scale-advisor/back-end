package org.scaleadvisor.backend.project.application.port.usecase.unitprocess

import org.scaleadvisor.backend.project.domain.UnitProcess

interface CreateUnitProcessUseCase {
    fun createAll(unitProcessList: List<UnitProcess>)
}