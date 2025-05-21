package org.scaleadvisor.backend.project.application.port.usecase.cocomomultiplier

import org.scaleadvisor.backend.project.domain.CocomoMultiplier
import org.scaleadvisor.backend.project.domain.enum.CocomoLevel
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface UpdateCocomoMultiplierUseCase {
    class UpdateCocomoMultiplierCommand(
        val projectId: ProjectId,
        var rcpx: CocomoLevel,
        var ruse: CocomoLevel,
        var pdif: CocomoLevel,
        var pers: CocomoLevel,
        var sced: CocomoLevel,
        var fcil: CocomoLevel
    )

    fun update(command: UpdateCocomoMultiplierCommand): CocomoMultiplier
}