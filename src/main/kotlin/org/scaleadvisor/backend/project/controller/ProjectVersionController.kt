package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.ProjectVersionAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.application.port.usecase.version.CreateProjectVersionUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.DeleteProjectVersionUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.UpdateProjectVersionUseCase
import org.scaleadvisor.backend.project.controller.request.version.CreateProjectVersionRequest
import org.scaleadvisor.backend.project.controller.request.version.UpdateProjectVersionRequest
import org.scaleadvisor.backend.project.controller.response.version.CreateProjectVersionResponse
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectVersionController(
    private val createProjectVersionUseCase: CreateProjectVersionUseCase,
    private val updateProjectVersionUseCase: UpdateProjectVersionUseCase,
    private val deleteProjectVersionUseCase: DeleteProjectVersionUseCase,
) : ProjectVersionAPI {
    override fun create(
        projectId: Long,
        versionNumber: String,
        request: CreateProjectVersionRequest
    ): SuccessResponse<CreateProjectVersionResponse> {
        val projectVersion = createProjectVersionUseCase.create(
            CreateProjectVersionUseCase.Command(
                ProjectVersion.of(projectId, versionNumber),
                request.requirementList,
                request.unitProcessList,
            )
        )

        return SuccessResponse.from(
            CreateProjectVersionResponse.from(projectVersion)
        )
    }

    override fun update(
        projectId: Long,
        versionNumber: String,
        request: UpdateProjectVersionRequest
    ) {
        updateProjectVersionUseCase.update(
            UpdateProjectVersionUseCase.Command(
                ProjectVersion.of(projectId, versionNumber),
                request.requirementList,
                request.unitProcessList,
                request.adjustmentFactorList,
            )
        )
    }

    override fun delete(
        projectId: Long,
        versionNumber: String
    ) {
        deleteProjectVersionUseCase.delete(
            projectId = ProjectId.from(projectId),
            versionNumber = versionNumber
        )
    }

}