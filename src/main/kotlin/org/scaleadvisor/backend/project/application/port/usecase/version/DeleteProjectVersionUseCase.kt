package org.scaleadvisor.backend.project.application.port.usecase.version

import org.scaleadvisor.backend.project.domain.id.ProjectId

interface DeleteProjectVersionUseCase {

    fun delete(projectId: ProjectId, versionNumber: String)

    fun deleteAll(projectId: ProjectId)

}