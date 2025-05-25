package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.ProjectVersionAPI
import org.scaleadvisor.backend.project.application.port.usecase.version.DeleteProjectVersionUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectVersionController(
    private val deleteProjectVersionUseCase: DeleteProjectVersionUseCase
) : ProjectVersionAPI {

    override fun delete(
        projectId: Long,
        versionNumber: String
    ) {
        deleteProjectVersionUseCase.delete(
            projectId = ProjectId.of(projectId),
            versionNumber = versionNumber
        )
    }

}