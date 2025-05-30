package org.scaleadvisor.backend.project.application.port.usecase.unitprocess

import org.scaleadvisor.backend.project.domain.ProjectVersion

interface ValidateUnitProcessUseCase {
    fun execute(projectVersion: ProjectVersion)
}