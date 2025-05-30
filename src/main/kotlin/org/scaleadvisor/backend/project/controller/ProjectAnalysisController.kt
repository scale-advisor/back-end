package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.ProjectAnalysisAPI
import org.scaleadvisor.backend.project.application.port.repository.unitprocess.UpdateUnitProcessPort
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.ClassifyUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.ETLUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.ValidateUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectAnalysisController(
    private val etlUnitProcess: ETLUnitProcessUseCase,
    private val validateUnitProcess: ValidateUnitProcessUseCase,
    private val classifyUnitProcess: ClassifyUnitProcessUseCase,
    private val updateUnitProcessUseCase: UpdateUnitProcessPort,
) : ProjectAnalysisAPI {

    override fun analyze(
        projectId: Long,
        versionNumber: String
    ) {
        val projectVersion = ProjectVersion.of(
            ProjectId.from(projectId),
            versionNumber
        )

        etlUnitProcess(projectVersion)
        validateUnitProcess(projectVersion)
        val unitProcessList: List<UnitProcess> = classifyUnitProcess(projectVersion)
        updateUnitProcessUseCase.updateAll(unitProcessList)

    }

}