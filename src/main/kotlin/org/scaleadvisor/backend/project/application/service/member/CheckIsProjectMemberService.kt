package org.scaleadvisor.backend.project.application.service.member

import org.scaleadvisor.backend.project.application.port.repository.member.CheckProjectMemberPort
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsProjectMemberUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service

@Service
private class CheckIsProjectMemberService(
    private val checkUsersProjectPort: CheckProjectMemberPort
) : CheckIsProjectMemberUseCase {
    override fun isProjectMember(
        userId: Long,
        projectId: ProjectId
    ): Boolean {
        return checkUsersProjectPort.exist(userId, projectId)
    }
}