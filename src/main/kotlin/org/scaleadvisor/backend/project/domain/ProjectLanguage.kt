package org.scaleadvisor.backend.project.domain

import org.scaleadvisor.backend.project.domain.enum.ProgramLanguage
import org.scaleadvisor.backend.project.domain.id.ProjectId

data class ProjectLanguage(
    val projectId: ProjectId,
    val language: ProgramLanguage,
    val rate: Int
)