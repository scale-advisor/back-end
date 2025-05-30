package org.scaleadvisor.backend.project.application.port.repository.unitprocess

import org.scaleadvisor.backend.project.domain.id.UnitProcessId

interface UpdateUnitProcessPort {

    fun updateAllIsAmbiguous(unitProcessIdList: List<UnitProcessId>)

}