package org.scaleadvisor.backend.project.controller.response.version

import org.scaleadvisor.backend.project.domain.ProjectVersion

data class CreateProjectVersionResponse(
    var versionNumber: String,
) {
    companion object {
        fun from(projectVersion: ProjectVersion): CreateProjectVersionResponse =
            CreateProjectVersionResponse(
                versionNumber = projectVersion.versionNumber
            )
    }
}
