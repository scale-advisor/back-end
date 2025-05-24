package org.scaleadvisor.backend.project.application.port.usecase.file

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface RemoveFileUseCase {

    fun removeAll(projectId: ProjectId)

}