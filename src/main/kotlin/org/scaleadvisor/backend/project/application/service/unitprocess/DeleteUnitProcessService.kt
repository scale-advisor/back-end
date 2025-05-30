package org.scaleadvisor.backend.project.application.service.unitprocess

import org.scaleadvisor.backend.project.application.port.repository.unitprocess.DeleteUnitProcessPort
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.DeleteUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.id.UnitProcessId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteUnitProcessService(
    private val deleteUnitProcessPort: DeleteUnitProcessPort,
): DeleteUnitProcessUseCase {
    override fun deleteAll(unitProcessIdList: List<UnitProcessId>) {
        deleteUnitProcessPort.deleteAll(unitProcessIdList)
    }
}