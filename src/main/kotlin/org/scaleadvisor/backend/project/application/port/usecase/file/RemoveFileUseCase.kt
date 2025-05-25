package org.scaleadvisor.backend.project.application.port.usecase.file

import org.scaleadvisor.backend.project.domain.Version
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface RemoveFileUseCase {

    fun remove(version: Version)

    fun removeAll(projectId: ProjectId)

}