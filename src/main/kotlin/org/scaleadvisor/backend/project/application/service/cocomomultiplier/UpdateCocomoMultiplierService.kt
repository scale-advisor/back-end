package org.scaleadvisor.backend.project.application.service.cocomomultiplier

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.cocomomultiplier.UpdateCocomoMultiplierPort
import org.scaleadvisor.backend.project.application.port.usecase.cocomomultiplier.FindCocomoMultiplierUseCase
import org.scaleadvisor.backend.project.application.port.usecase.cocomomultiplier.UpdateCocomoMultiplierUseCase
import org.scaleadvisor.backend.project.domain.CocomoMultiplier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class UpdateCocomoMultiplierService(
    private val updateCocomoMultiplierPort: UpdateCocomoMultiplierPort,
    private val findCocomoMultiplierUseCase: FindCocomoMultiplierUseCase
): UpdateCocomoMultiplierUseCase {

    override fun update(command: UpdateCocomoMultiplierUseCase.UpdateCocomoMultiplierCommand
    ): CocomoMultiplier {
        val cocomoMultiplier = findCocomoMultiplierUseCase.find(command.projectId)
            ?: throw NotFoundException("프로젝트를 찾을 수 없습니다. (id=$command.projectId)")

        cocomoMultiplier.update(
            rcpx = command.rcpx,
            ruse = command.ruse,
            pdif = command.pdif,
            pers = command.pers,
            sced = command.sced,
            fcil = command.fcil
        )

        updateCocomoMultiplierPort.update(cocomoMultiplier)

        return cocomoMultiplier
    }

}