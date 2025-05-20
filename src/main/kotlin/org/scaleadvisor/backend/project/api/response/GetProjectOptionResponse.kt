package org.scaleadvisor.backend.project.api.response

import org.scaleadvisor.backend.project.application.port.usecase.GetProjectOptionUseCase
import org.scaleadvisor.backend.project.domain.ProjectLanguage
import org.scaleadvisor.backend.project.domain.enum.CocomoType
import org.scaleadvisor.backend.project.domain.id.ProjectId

data class GetProjectOptionResponse(
    var projectId: ProjectId,
    var unitCost: Int,
    var teamSize: Int,
    var cocomoType: CocomoType,
    var languageList: List<ProjectLanguageDTO>,
) {
    data class ProjectLanguageDTO(
        val language: String,
        val rate: Int
    ) {
        companion object {
            @JvmStatic
            fun from(projectLanguage: ProjectLanguage): ProjectLanguageDTO = ProjectLanguageDTO(
                language = projectLanguage.language.name,
                rate = projectLanguage.rate
            )
        }
    }
    companion object {
        @JvmStatic
        fun from(result: GetProjectOptionUseCase.Result): GetProjectOptionResponse =
            GetProjectOptionResponse(
                projectId = result.projectFactor.projectId,
                unitCost = result.projectFactor.unitCost,
                teamSize = result.projectFactor.teamSize,
                cocomoType = result.projectFactor.cocomoType,
                languageList = result.projectLanguageList.map { ProjectLanguageDTO.from(it) }
            )
    }
}