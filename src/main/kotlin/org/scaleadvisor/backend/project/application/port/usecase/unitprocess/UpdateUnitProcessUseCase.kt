package org.scaleadvisor.backend.project.application.port.usecase.unitprocess

import org.scaleadvisor.backend.project.domain.UnitProcess
import org.scaleadvisor.backend.project.domain.id.UnitProcessId

interface UpdateUnitProcessUseCase {
    fun updateAll(unitProcessList: List<UnitProcess>)
    fun updateAllIsAmbiguous(unitProcessIdList: List<UnitProcessId>)
}