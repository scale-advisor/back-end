package org.scaleadvisor.backend.project.application.port.repository.file

import org.scaleadvisor.backend.project.domain.Version
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface DeleteFilePort {

    fun delete(version: Version)

    fun deleteAll(projectId: ProjectId)

}