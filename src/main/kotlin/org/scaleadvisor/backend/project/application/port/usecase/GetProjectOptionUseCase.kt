package org.scaleadvisor.backend.project.application.port.usecase

import org.scaleadvisor.backend.project.domain.ProjectFactor
import org.scaleadvisor.backend.project.domain.ProjectLanguage
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface GetProjectOptionUseCase {
    data class Result(
        val projectFactor: ProjectFactor,
        val projectLanguageList: List<ProjectLanguage>,
    )

    fun find(projectId: ProjectId): Result

}