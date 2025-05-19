package org.scaleadvisor.backend.project.application.service

import org.scaleadvisor.backend.global.util.IdUtil
import org.scaleadvisor.backend.project.application.port.repository.CreateProjectRepository
import org.scaleadvisor.backend.project.application.port.repository.CreateUserProjectRepository
import org.scaleadvisor.backend.project.application.port.usecase.CreateProjectUseCase
import org.scaleadvisor.backend.project.domain.Project
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
private class CreateProjectService(
    private val createProjectRepository: CreateProjectRepository,
    private val createUserProjectRepository: CreateUserProjectRepository
) : CreateProjectUseCase{

    override fun create(command: CreateProjectUseCase.CreateProjectCommand): Project {

        var project = Project(
            id = IdUtil.generateId(),
            name = command.name,
            description = command.description,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        project = createProjectRepository.createProject(project)

        createUserProjectRepository.createUserProject(command.userId, project.id)

        return project
    }

}