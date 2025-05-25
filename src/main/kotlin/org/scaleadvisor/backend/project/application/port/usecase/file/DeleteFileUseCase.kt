package org.scaleadvisor.backend.project.application.port.usecase.file

import org.scaleadvisor.backend.project.domain.Version
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface DeleteFileUseCase {

    fun delete(version: Version)

    fun deleteAll(projectId: ProjectId)

}