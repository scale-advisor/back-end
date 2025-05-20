package org.scaleadvisor.backend.project.application.port.usecase

import org.scaleadvisor.backend.project.domain.ProjectLanguage
import org.scaleadvisor.backend.project.domain.enum.CocomoType
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface UpdateProjectOptionUseCase {

    data class Command(
        val projectId: ProjectId,
        val unitCost: Int?,
        val teamSize: Int?,
        val cocomoType: CocomoType?,
        val projectLanguageList: List<ProjectLanguage>?
    )

    fun update(command: Command)

}