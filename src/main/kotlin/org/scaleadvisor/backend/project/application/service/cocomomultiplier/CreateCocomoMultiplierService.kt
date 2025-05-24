package org.scaleadvisor.backend.project.application.service.cocomomultiplier

import org.scaleadvisor.backend.project.application.port.repository.cocomomultiplier.CreateCocomoMultiplierPort
import org.scaleadvisor.backend.project.application.port.usecase.cocomomultiplier.CreateCocomoMultiplierUseCase
import org.scaleadvisor.backend.project.domain.CocomoMultiplier
import org.scaleadvisor.backend.project.domain.id.CocomoMultiplierId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
private class CreateCocomoMultiplierService(
    private val createCocomoMultiplierPort: CreateCocomoMultiplierPort
): CreateCocomoMultiplierUseCase {

    override fun create(command: CreateCocomoMultiplierUseCase.CreateCocomoMultiplierCommand
    ): CocomoMultiplier {
        val newId = CocomoMultiplierId.newId()
        val now = LocalDateTime.now()

        val cocomoMultiplier = CocomoMultiplier(
            cocomoMultiplierId = newId,
            projectId = command.projectId,
            rcpx = command.rcpx,
            ruse = command.ruse,
            pdif = command.pdif,
            pers = command.pers,
            sced = command.sced,
            fcil = command.fcil,
            createdAt = now,
            updatedAt = now
        )

        createCocomoMultiplierPort.create(cocomoMultiplier)

        return cocomoMultiplier
    }
}