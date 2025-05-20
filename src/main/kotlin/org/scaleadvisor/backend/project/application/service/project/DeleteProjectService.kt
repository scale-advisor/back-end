package org.scaleadvisor.backend.project.application.service.project

import org.scaleadvisor.backend.global.exception.model.ValidationException
import org.scaleadvisor.backend.project.application.port.repository.project.DeleteProjectPort
import org.scaleadvisor.backend.project.application.port.repository.member.DeleteUserProjectPort
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsProjectMemberUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.DeleteProjectUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.GetProjectUseCase
import org.scaleadvisor.backend.project.domain.Project
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service

@Service
private class DeleteProjectService(
    private val checkIsProjectMemberUseCase: CheckIsProjectMemberUseCase,
    private val getProjectUseCase: GetProjectUseCase,
    private val deleteProjectPort: DeleteProjectPort,
    private val deleteUserProjectPort: DeleteUserProjectPort
) : DeleteProjectUseCase {
    override fun delete(userId: Long, projectId: ProjectId) {
        val isProjectMember = checkIsProjectMemberUseCase.isProjectMember(
            userId = userId,
            projectId = projectId
        )
        if (!isProjectMember) {
            throw ValidationException("프로젝트 멤버가 아닙니다.")
        }

        val project: Project = getProjectUseCase.find(projectId)
            ?: throw NoSuchElementException("해당 프로젝트가 존재하지 않습니다.")

        deleteUserProjectPort.delete(userId, projectId)
        deleteProjectPort.delete(project.id)
    }
}