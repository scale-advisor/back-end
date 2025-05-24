package org.scaleadvisor.backend.project.application.service.project

import org.scaleadvisor.backend.project.application.port.repository.member.CreateProjectMemberPort
import org.scaleadvisor.backend.project.application.port.repository.project.CreateProjectPort
import org.scaleadvisor.backend.project.application.port.repository.version.CreateVersionPort
import org.scaleadvisor.backend.project.application.port.usecase.project.CreateProjectUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.GetProjectUseCase
import org.scaleadvisor.backend.project.domain.Project
import org.scaleadvisor.backend.project.domain.Version
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
private class CreateProjectService(
    private val getProjectUseCase: GetProjectUseCase,
    private val createProjectPort: CreateProjectPort,
    private val createProjectMemberPort: CreateProjectMemberPort,
    private val createVersionPort: CreateVersionPort
) : CreateProjectUseCase{

    override fun create(command: CreateProjectUseCase.CreateProjectCommand): Project {

        var project = Project(
            id = ProjectId.newId(),
            name = command.name,
            description = command.description,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        createProjectPort.create(project)
        project = getProjectUseCase.find(project.id)
            ?: throw Exception("프로젝트 생성과정에 문제가 발생했습니다")

        createProjectMemberPort.create(command.userId, project.id)

        val version = Version(
            projectId = project.id,
            versionNumber = "1.0"
        )
        createVersionPort.create(version)

        return project
    }

}