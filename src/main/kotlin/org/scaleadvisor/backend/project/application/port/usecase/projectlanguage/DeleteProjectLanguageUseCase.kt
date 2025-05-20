package org.scaleadvisor.backend.project.application.port.usecase.projectlanguage

import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface DeleteProjectLanguageUseCase {

    fun deleteAll(projectId: ProjectId)

}