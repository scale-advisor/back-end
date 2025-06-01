package org.scaleadvisor.backend.project.application.port.usecase.version

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface GenerateProjectVersionUseCase {
    fun generateNextMajorVersion(projectId: ProjectId): ProjectVersion
    fun generateNextMinorVersion(projectVersion: ProjectVersion): ProjectVersion
}