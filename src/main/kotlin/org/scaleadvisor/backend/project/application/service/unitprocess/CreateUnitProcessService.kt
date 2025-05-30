package org.scaleadvisor.backend.project.application.service.unitprocess

import org.scaleadvisor.backend.project.application.port.repository.unitprocess.CreateUnitProcessPort
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.CreateUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class CreateUnitProcessService(
    private val createUnitProcessPort: CreateUnitProcessPort,
): CreateUnitProcessUseCase {

    override fun createAll(unitProcessList: List<UnitProcess>) {
        return createUnitProcessPort.createAll(unitProcessList)
    }

}