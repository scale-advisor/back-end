package org.scaleadvisor.backend.project.application.port.usecase.version

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteVersionUseCase {

    fun deleteAll(projectId: ProjectId)

}