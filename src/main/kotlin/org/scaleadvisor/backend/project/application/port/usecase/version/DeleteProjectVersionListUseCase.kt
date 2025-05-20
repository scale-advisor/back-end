package org.scaleadvisor.backend.project.application.port.usecase.version

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteProjectVersionListUseCase {

    fun deleteAll(projectId: ProjectId)

}