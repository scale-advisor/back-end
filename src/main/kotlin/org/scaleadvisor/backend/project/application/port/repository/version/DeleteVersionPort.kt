package org.scaleadvisor.backend.project.application.port.repository.version

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface DeleteVersionPort {

    fun delete(projectId: ProjectId, projectVersion: ProjectVersion)

    fun deleteAll(projectId: ProjectId)

    fun deleteAll(projectId: ProjectId, majorNumber: Int)

}