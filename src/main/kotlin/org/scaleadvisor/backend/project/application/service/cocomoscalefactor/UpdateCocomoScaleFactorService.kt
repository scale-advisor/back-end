package org.scaleadvisor.backend.project.application.service.cocomoscalefactor

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.cocomoscalefactor.UpdateCocomoScaleFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.cocomoscalefactor.FindCocomoScaleFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.cocomoscalefactor.UpdateCocomoScaleFactorUseCase
import org.scaleadvisor.backend.project.domain.CocomoScaleFactor
import org.springframework.stereotype.Service

@Service
private class UpdateScaleFactorService(
    private val updateCocomoScaleFactorPort: UpdateCocomoScaleFactorPort,
    private val findCocomoScaleFactorUseCase: FindCocomoScaleFactorUseCase
): UpdateCocomoScaleFactorUseCase {
    override fun update(command: UpdateCocomoScaleFactorUseCase.UpdateCocomoScaleFactorCommand): CocomoScaleFactor {
        val cocomoScaleFactor = findCocomoScaleFactorUseCase.find(command.projectId)
            ?: throw NotFoundException("프로젝트를 찾을 수 없습니다. (id=$command.projectId)")

        cocomoScaleFactor.update(
            prec = command.prec,
            flex = command.flex,
            resl = command.resl,
            team = command.team,
            pmat = command.pmat
        )

        updateCocomoScaleFactorPort.update(cocomoScaleFactor)

        return cocomoScaleFactor
    }
}