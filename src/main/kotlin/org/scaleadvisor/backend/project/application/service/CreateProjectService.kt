package org.scaleadvisor.backend.project.application.service

import org.scaleadvisor.backend.project.application.port.repository.CreateProjectRepository
import org.scaleadvisor.backend.project.application.port.usecase.CreateProjectUseCase
import org.scaleadvisor.backend.project.domain.Project
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class CreateProjectService(
    private val createProjectRepository: CreateProjectRepository,
) : CreateProjectUseCase{

    override fun execute(command: CreateProjectUseCase.CreateProjectCommand): Project {

        val project = Project(
            id = ProjectId.newId(),
            name = command.name,
            description = command.description,
            createdAt = OffsetDateTime.now(),
            updatedAt = OffsetDateTime.now()
        )

        return createProjectRepository.create(project)
    }

}