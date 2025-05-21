package org.scaleadvisor.backend.project.application.port.usecase.cocomoscalefactor

import org.scaleadvisor.backend.project.domain.CocomoScaleFactor
import org.scaleadvisor.backend.project.domain.enum.CocomoLevel
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface UpdateCocomoScaleFactorUseCase {
    class UpdateCocomoScaleFactorCommand(
        val projectId: ProjectId,
        val prec: CocomoLevel,
        val flex: CocomoLevel,
        val resl: CocomoLevel,
        val team: CocomoLevel,
        val pmat: CocomoLevel
    )

    fun update(command: UpdateCocomoScaleFactorCommand): CocomoScaleFactor
}