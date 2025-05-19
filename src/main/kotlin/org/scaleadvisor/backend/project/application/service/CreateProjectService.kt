package org.scaleadvisor.backend.project.application.service

import org.scaleadvisor.backend.global.util.IdUtil
import org.scaleadvisor.backend.project.application.port.repository.CreateProjectRepository
import org.scaleadvisor.backend.project.application.port.usecase.CreateProjectUseCase
import org.scaleadvisor.backend.project.domain.Project
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
private class CreateProjectService(
    private val createProjectRepository: CreateProjectRepository,
) : CreateProjectUseCase{

    override fun create(command: CreateProjectUseCase.CreateProjectCommand): Project {

        val project = Project(
            id = IdUtil.generateId(),
            name = command.name,
            description = command.description,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        return createProjectRepository.createProject(project)
    }

}