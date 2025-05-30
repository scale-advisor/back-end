package org.scaleadvisor.backend.project.application.port.usecase.unitprocess

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.UnitProcess

interface GetUnitProcessUseCase {
    fun findAll(projectVersion: ProjectVersion): List<UnitProcess>
}