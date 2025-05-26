package org.scaleadvisor.backend.project.application.port.usecase.file

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface RemoveFileUseCase {

    fun remove(projectVersion: ProjectVersion)

    fun removeAll(projectId: ProjectId)

}