package org.scaleadvisor.backend.project.application.port.repository.projectlanguage

import org.scaleadvisor.backend.project.domain.ProjectLanguage
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface GetProjectLanguagePort {

    fun findAll(projectId: ProjectId): List<ProjectLanguage>

}