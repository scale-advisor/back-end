package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.ProjectAnalysisAPI
import org.scaleadvisor.backend.project.application.port.usecase.project.AnalyzeProjectUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectAnalysisController(
    private val analyzeProjectUseCase: AnalyzeProjectUseCase,
) : ProjectAnalysisAPI {

    override fun analyze(
        projectId: Long,
        versionNumber: String
    ) {
        val projectVersion = ProjectVersion.of(
            ProjectId.from(projectId),
            versionNumber
        )

        analyzeProjectUseCase(projectVersion)
    }

}