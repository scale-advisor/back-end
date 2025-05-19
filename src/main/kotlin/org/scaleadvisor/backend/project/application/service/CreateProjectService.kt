package org.scaleadvisor.backend.project.application.service

import org.scaleadvisor.backend.project.application.port.repository.CreateProjectPort
import org.scaleadvisor.backend.project.application.port.repository.CreateUserProjectPort
import org.scaleadvisor.backend.project.application.port.usecase.CreateProjectUseCase
import org.scaleadvisor.backend.project.application.port.usecase.GetProjectUseCase
import org.scaleadvisor.backend.project.domain.Project
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
private class CreateProjectService(
    private val getProjectUseCase: GetProjectUseCase,
    private val createProjectPort: CreateProjectPort,
    private val createUserProjectPort: CreateUserProjectPort
) : CreateProjectUseCase{

    override fun create(command: CreateProjectUseCase.CreateProjectCommand): Project {

        var project = Project(
            id = ProjectId.newId(),
            name = command.name,
            description = command.description,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        createProjectPort.createProject(project)
        project = getProjectUseCase.find(project.id)
            ?: throw Exception("프로젝트 생성과정에 문제가 발생했습니다")

        createUserProjectPort.createUserProject(command.userId, project.id)

        return project
    }

}