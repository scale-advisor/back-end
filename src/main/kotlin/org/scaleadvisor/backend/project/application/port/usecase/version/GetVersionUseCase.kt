package org.scaleadvisor.backend.project.application.port.usecase.version

import org.scaleadvisor.backend.project.domain.VersionNumber
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface GetVersionUseCase {

    fun findLatest(projectId: ProjectId): VersionNumber

    fun findAll(projectId: ProjectId): List<VersionNumber>

}