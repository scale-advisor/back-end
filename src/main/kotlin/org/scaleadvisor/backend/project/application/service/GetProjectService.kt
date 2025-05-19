package org.scaleadvisor.backend.project.application.service

import org.scaleadvisor.backend.project.application.port.repository.GetProjectRepository
import org.scaleadvisor.backend.project.application.port.usecase.GetProjectUseCase
import org.scaleadvisor.backend.project.domain.Project
import org.springframework.stereotype.Service

@Service
private class GetProjectService(
    private val getProjectRepository: GetProjectRepository
) : GetProjectUseCase {
    override fun findAll(userId: Long): List<Project> {
        return getProjectRepository.findAll(userId)
    }
}