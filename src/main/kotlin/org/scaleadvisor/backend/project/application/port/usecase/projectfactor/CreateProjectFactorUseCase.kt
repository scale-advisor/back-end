package org.scaleadvisor.backend.project.application.port.usecase.projectfactor

import org.scaleadvisor.backend.project.domain.enum.CocomoType
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface CreateProjectFactorUseCase {
    class CreateProjectFactorCommand(
        var projectId: ProjectId,
        var unitCost: Int,
        var teamSize: Int,
        var cocomoType: CocomoType,
    )

    fun create(command: CreateProjectFactorCommand)

}