package org.scaleadvisor.backend.project.application.port.repository.unitprocess

import org.scaleadvisor.backend.project.domain.id.UnitProcessId

interface DeleteUnitProcessPort {
    fun deleteAll(unitProcessIdList: List<UnitProcessId>)
}