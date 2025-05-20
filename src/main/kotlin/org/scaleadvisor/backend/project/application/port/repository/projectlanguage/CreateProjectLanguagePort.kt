package org.scaleadvisor.backend.project.application.port.repository.projectlanguage

import org.scaleadvisor.backend.project.domain.ProjectLanguage
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface CreateProjectLanguagePort {

    fun createAll(projectId: ProjectId, projectLanguageList: List<ProjectLanguage>)

}