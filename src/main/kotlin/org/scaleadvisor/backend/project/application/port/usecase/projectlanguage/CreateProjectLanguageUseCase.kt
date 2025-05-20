package org.scaleadvisor.backend.project.application.port.usecase.projectlanguage

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface CreateProjectLanguageUseCase {

    data class Command(
        var projectId: ProjectId,
        var languageList: List<String>
    )

    fun create(command: Command)

}