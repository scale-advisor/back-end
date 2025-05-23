package org.scaleadvisor.backend.project.application.port.usecase.projectlanguage

import org.scaleadvisor.backend.project.domain.ProjectLanguage
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface CreateProjectLanguageUseCase {

    fun createAllWithOutRate(
        projectId: ProjectId,
        languageList: List<String>
    )

    fun createAll(
        projectId: ProjectId,
        projectLanguageList: List<ProjectLanguage>
    )

}