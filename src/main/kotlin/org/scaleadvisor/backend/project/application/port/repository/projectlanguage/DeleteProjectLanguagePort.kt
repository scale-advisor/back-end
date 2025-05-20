package org.scaleadvisor.backend.project.application.port.repository.projectlanguage

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteProjectLanguagePort {

    fun deleteAll(projectId: ProjectId)

}