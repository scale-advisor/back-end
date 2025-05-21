package org.scaleadvisor.backend.project.application.port.usecase.cocomoscalefactor

import org.scaleadvisor.backend.project.domain.CocomoScaleFactor
import org.scaleadvisor.backend.project.domain.enum.CocomoLevel
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface CreateCocomoScaleFactorUseCase {
    class CreateCocomoScaleFactorCommand(
        val projectId: ProjectId,
        val prec: CocomoLevel,
        val flex: CocomoLevel,
        val resl: CocomoLevel,
        val team: CocomoLevel,
        val pmat: CocomoLevel
    )

    fun create(command: CreateCocomoScaleFactorCommand): CocomoScaleFactor
}