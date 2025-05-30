package org.scaleadvisor.backend.project.application.port.repository.unitprocess

import org.scaleadvisor.backend.project.domain.UnitProcess
import org.scaleadvisor.backend.project.domain.id.UnitProcessId

interface UpdateUnitProcessPort {
    fun updateAll(unitProcessList: List<UnitProcess>)

    fun updateAllIsAmbiguous(unitProcessIdList: List<UnitProcessId>)

}