package org.scaleadvisor.backend.project.application.service.cocomoscalefactor

import org.scaleadvisor.backend.project.application.port.repository.cocomoscalefactor.CreateCocomoScaleFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.cocomoscalefactor.CreateCocomoScaleFactorUseCase
import org.scaleadvisor.backend.project.domain.CocomoScaleFactor
import org.scaleadvisor.backend.project.domain.id.CocomoScaleFactorId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
private class CreateCocomoScaleFactorService(
    private val createCocomoScaleFactorPort: CreateCocomoScaleFactorPort
): CreateCocomoScaleFactorUseCase {

    override fun create(command: CreateCocomoScaleFactorUseCase.CreateCocomoScaleFactorCommand
    ): CocomoScaleFactor {
        val newId = CocomoScaleFactorId.newId()
        val now = LocalDateTime.now()

        val cocomoScaleFactor = CocomoScaleFactor(
            cocomoScaleFactorId = newId,
            projectId = command.projectId,
            prec = command.prec,
            flex = command.flex,
            resl = command.resl,
            team = command.team,
            pmat = command.pmat,
            createdAt = now,
            updatedAt = now
        )

        createCocomoScaleFactorPort.create(cocomoScaleFactor)

        return cocomoScaleFactor
    }
}