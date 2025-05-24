package org.scaleadvisor.backend.project.application.service.project

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.global.exception.model.ValidationException
import org.scaleadvisor.backend.project.application.port.repository.project.UpdateProjectPort
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsProjectMemberUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.GetProjectUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.UpdateProjectUseCase
import org.scaleadvisor.backend.project.domain.Project
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
private class UpdateProjectService(
    private val checkIsProjectMemberUseCase: CheckIsProjectMemberUseCase,
    private val getProjectUseCase: GetProjectUseCase,
    private val updateProjectPort: UpdateProjectPort
) : UpdateProjectUseCase {

    override fun update(command: UpdateProjectUseCase.UpdateProjectCommand): Project {
        val isProjectMember = checkIsProjectMemberUseCase.isProjectMember(
            userId = command.userId,
            projectId = command.projectId
        )
        if (!isProjectMember) {
            throw ValidationException("프로젝트 멤버가 아닙니다.")
        }

        var project = getProjectUseCase.find(command.projectId)
        if (project == null) {
            throw NotFoundException("프로젝트가 존재하지 않습니다.")
        }

        project = Project(
            id = project.id,
            name = command.name,
            description = command.description,
            createdAt = project.createdAt,
            updatedAt = LocalDateTime.now()
        )

        updateProjectPort.update(project)
        return getProjectUseCase.find(project.id)
            ?: throw Exception("프로젝트 업데이트 과정에 문제가 발생했습니다.")
    }

}