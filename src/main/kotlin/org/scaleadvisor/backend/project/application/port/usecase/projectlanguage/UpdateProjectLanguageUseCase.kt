package org.scaleadvisor.backend.project.application.port.usecase.projectlanguage

import org.scaleadvisor.backend.project.domain.ProjectLanguage
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface UpdateProjectLanguageUseCase {

    fun updateAll(
        projectId: ProjectId,
        projectLanguageList: List<ProjectLanguage>
    )

}