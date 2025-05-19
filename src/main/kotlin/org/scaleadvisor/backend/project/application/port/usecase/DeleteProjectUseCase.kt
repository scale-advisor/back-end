package org.scaleadvisor.backend.project.application.port.usecase

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteProjectUseCase {

    fun delete(userId: Long, projectId: ProjectId)

}