package org.scaleadvisor.backend.project.application.port.usecase.unitprocess

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.UnitProcess

interface ClassifyUnitProcessUseCase {
    operator fun invoke(projectVersion: ProjectVersion): List<UnitProcess>
}