package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.ProjectUnitProcessAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.GetUnitProcessUseCase
import org.scaleadvisor.backend.project.controller.response.unitprocess.GetAllUnitProcessResponse
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectUnitProcessController(
    private val getUnitProcessUseCase: GetUnitProcessUseCase,
) : ProjectUnitProcessAPI {
    override fun findAll(
        projectId: Long,
        versionNumber: String
    ): SuccessResponse<GetAllUnitProcessResponse> {
        val projectVersion = ProjectVersion.of(projectId, versionNumber)
        val unitProcessList = getUnitProcessUseCase.findAll(projectVersion)
        return SuccessResponse.from(
            GetAllUnitProcessResponse.from(
                unitProcessList
            )
        )
    }

}