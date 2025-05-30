package org.scaleadvisor.backend.project.application.port.usecase.unitprocess

import org.scaleadvisor.backend.project.domain.id.UnitProcessId

interface DeleteUnitProcessUseCase {
    fun deleteAll(unitProcessIdList: List<UnitProcessId>)
}