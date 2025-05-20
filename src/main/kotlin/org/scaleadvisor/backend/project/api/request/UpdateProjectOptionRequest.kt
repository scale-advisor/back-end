package org.scaleadvisor.backend.project.api.request

import org.scaleadvisor.backend.project.domain.enum.CocomoType

data class UpdateProjectOptionRequest(
    val unitCost: Int?,
    val teamSize: Int?,
    val cocomoType: CocomoType?,
    val languageList: List<ProjectLanguageDTO>?
) {
    data class ProjectLanguageDTO(
        val language: String,
        val rate: Int
    )
}
