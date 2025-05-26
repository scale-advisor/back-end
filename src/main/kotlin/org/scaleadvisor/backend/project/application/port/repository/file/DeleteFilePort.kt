package org.scaleadvisor.backend.project.application.port.repository.file

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface DeleteFilePort {

    fun delete(projectVersion: ProjectVersion)

    fun deleteAll(projectId: ProjectId)

}