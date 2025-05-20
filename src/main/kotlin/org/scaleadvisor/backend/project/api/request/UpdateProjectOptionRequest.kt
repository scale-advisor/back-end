package org.scaleadvisor.backend.project.api.request

import org.scaleadvisor.backend.project.domain.ProjectLanguage
import org.scaleadvisor.backend.project.domain.enum.CocomoType
import org.scaleadvisor.backend.project.domain.enum.ProgramLanguage

data class UpdateProjectOptionRequest(
    val unitCost: Int?,
    val teamSize: Int?,
    val cocomoType: CocomoType?,
    val projectLanguageList: List<ProjectLanguageDTO>?
) {
    data class ProjectLanguageDTO(
        val language: String,
        val rate: Int
    ) {
        fun toDomain(): ProjectLanguage = ProjectLanguage(
            language = ProgramLanguage.valueOf(language.uppercase()),
            rate = rate
        )
    }
}
