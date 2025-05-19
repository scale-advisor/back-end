package org.scaleadvisor.backend.project.application.port.usecase

import org.scaleadvisor.backend.project.domain.Project
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface UpdateProjectUseCase {
    class UpdateProjectCommand(
        val userId: Long,
        val projectId: ProjectId,
        val name: String,
        val description: String?
    )

    fun update(command: UpdateProjectCommand): Project

}