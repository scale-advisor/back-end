package org.scaleadvisor.backend.project.application.port.usecase.unitprocess

import org.scaleadvisor.backend.project.domain.ProjectVersion

fun interface UpdateUndefinedUnitProcessUseCase {
    fun markAmbiguousForUndefined(projectVersion: ProjectVersion)
}