package org.scaleadvisor.backend.project.application.port.repository.version

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteVersionPort {

    fun deleteAll(projectId: ProjectId)

}