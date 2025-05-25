package org.scaleadvisor.backend.project.application.port.usecase.version

import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.vo.VersionNumber

interface GenerateVersionUseCase {
    fun generateNextMajorVersion(projectId: ProjectId): VersionNumber
    fun generateNextMinorVersion(projectId: ProjectId): VersionNumber
}