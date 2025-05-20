package org.scaleadvisor.backend.project.application.port.usecase.cocomoscalefactor

import org.scaleadvisor.backend.project.domain.CocomoScaleFactor
import org.scaleadvisor.backend.project.domain.enum.CocomoScaleFactorLevel
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface CreateCocomoScaleFactorUseCase {
    class CreateCocomoScaleFactorCommand(
        val projectId: ProjectId,
        val prec: CocomoScaleFactorLevel,
        val flex: CocomoScaleFactorLevel,
        val resl: CocomoScaleFactorLevel,
        val team: CocomoScaleFactorLevel,
        val pmat: CocomoScaleFactorLevel
    )

    fun create(command: CreateCocomoScaleFactorCommand): CocomoScaleFactor
}