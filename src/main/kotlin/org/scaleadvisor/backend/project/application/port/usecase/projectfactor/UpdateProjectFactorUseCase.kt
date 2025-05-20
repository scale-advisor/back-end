package org.scaleadvisor.backend.project.application.port.usecase.projectfactor

import org.scaleadvisor.backend.project.domain.enum.CocomoType
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface UpdateProjectFactorUseCase {
    data class Command(
        val projectId: ProjectId,
        val unitCost: Int?,
        val teamSize: Int?,
        val cocomoType: CocomoType?,
    )

    fun update(command: Command)

}