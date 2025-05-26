package org.scaleadvisor.backend.project.application.port.repository.version

import org.scaleadvisor.backend.project.domain.ProjectVersion

fun interface CreateProjectVersionPort {

    fun create(projectVersion: ProjectVersion)

}