package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.ProjectAnalysisAPI
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.ETLUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.ValidateUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectAnalysisController(
    private val etlUnitProcessUseCase: ETLUnitProcessUseCase,
    private val validateUnitProcessUseCase: ValidateUnitProcessUseCase,
) : ProjectAnalysisAPI {

    override fun analyze(
        projectId: Long,
        versionNumber: String
    ) {
        val projectVersion = ProjectVersion.of(
            ProjectId.from(projectId),
            versionNumber
        )

        etlUnitProcessUseCase.execute(projectVersion)
        validateUnitProcessUseCase.execute(projectVersion)
    }

}