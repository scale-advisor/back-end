package org.scaleadvisor.backend.project.application.port.usecase.projectlanguage

import org.scaleadvisor.backend.project.domain.ProjectLanguage
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface GetProjectLanguageUseCase {

    fun findAll(projectId: ProjectId): List<ProjectLanguage>

}