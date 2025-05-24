package org.scaleadvisor.backend.project.application.port.repository.file

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface RemoveFilePort {
    fun removeAll(projectId: ProjectId)
}