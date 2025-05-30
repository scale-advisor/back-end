package org.scaleadvisor.backend.project.application.service.unitprocess

import org.scaleadvisor.backend.project.application.port.repository.unitprocess.UpdateUnitProcessPort
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.UpdateUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.scaleadvisor.backend.project.domain.id.UnitProcessId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class UpdateUnitProcessService(
    private val updateUnitProcessPort: UpdateUnitProcessPort,
): UpdateUnitProcessUseCase {
    override fun updateAll(unitProcessList: List<UnitProcess>) {
        updateUnitProcessPort.updateAll(unitProcessList)
    }

    override fun updateAllIsAmbiguous(unitProcessIdList: List<UnitProcessId>) {
        updateUnitProcessPort.updateAllIsAmbiguous(unitProcessIdList)
    }
}