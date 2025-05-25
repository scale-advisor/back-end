package org.scaleadvisor.backend.project.application.port.repository.file

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface RemoveFilePort {

    fun remove(projectId: ProjectId, projectVersion: ProjectVersion)
    fun removeAll(projectId: ProjectId)

}