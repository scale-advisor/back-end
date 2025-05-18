package org.scaleadvisor.backend.project.application.service

import org.scaleadvisor.backend.project.api.response.FindAllProjectResponse
import org.scaleadvisor.backend.project.application.port.usecase.GetProjectUseCase

class GetProjectService : GetProjectUseCase {
    override fun findAll(userId: Long): List<FindAllProjectResponse.ProjectDTO> {
        TODO("Not yet implemented")
    }
}