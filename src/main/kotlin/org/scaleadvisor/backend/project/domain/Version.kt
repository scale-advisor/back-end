package org.scaleadvisor.backend.project.domain

import org.scaleadvisor.backend.project.domain.id.ProjectId

data class Version(
    val projectId: ProjectId,
    var versionNumber: String,
)
