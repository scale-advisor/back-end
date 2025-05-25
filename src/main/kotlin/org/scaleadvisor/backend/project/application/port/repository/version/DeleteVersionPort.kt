package org.scaleadvisor.backend.project.application.port.repository.version

import org.scaleadvisor.backend.project.domain.Version
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface DeleteVersionPort {

    fun delete(version: Version)

    fun deleteAll(projectId: ProjectId)

    fun deleteAll(projectId: ProjectId, majorNumber: Int)

}