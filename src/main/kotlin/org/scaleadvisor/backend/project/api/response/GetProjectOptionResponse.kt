package org.scaleadvisor.backend.project.api.response

import org.scaleadvisor.backend.project.domain.ProjectFactor
import org.scaleadvisor.backend.project.domain.enum.CocomoType
import org.scaleadvisor.backend.project.domain.id.ProjectId

data class GetProjectOptionResponse(
    var projectId: ProjectId,
    var unitCost: Int,
    var teamSize: Int,
    var cocomoType: CocomoType,
    var languageList: List<String>,
) {
    companion object {
        @JvmStatic
        fun of(projectFactor: ProjectFactor): GetProjectOptionResponse =
            GetProjectOptionResponse(
                projectId = projectFactor.projectId,
                unitCost = projectFactor.unitCost,
                teamSize = projectFactor.teamSize,
                cocomoType = projectFactor.cocomoType,
                languageList = listOf()
            )
    }
}