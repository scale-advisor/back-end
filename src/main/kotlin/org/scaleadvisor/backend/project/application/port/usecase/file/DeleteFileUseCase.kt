package org.scaleadvisor.backend.project.application.port.usecase.file

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface DeleteFileUseCase {

    fun delete(projectVersion: ProjectVersion)

    fun deleteAll(projectId: ProjectId)

}