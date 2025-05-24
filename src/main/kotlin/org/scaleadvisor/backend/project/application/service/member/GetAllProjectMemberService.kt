package org.scaleadvisor.backend.project.application.service.member

import org.scaleadvisor.backend.project.application.port.repository.member.GetAllProjectMemberPort
import org.scaleadvisor.backend.project.application.port.usecase.member.GetAllProjectMemberUseCase
import org.scaleadvisor.backend.project.domain.ProjectMember
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetAllProjectMemberService(
    private val getAllProjectMemberPort: GetAllProjectMemberPort
): GetAllProjectMemberUseCase {

    override fun findAllByProjectId(
        projectId: ProjectId,
        offset: Int,
        limit: Int
    ): List<ProjectMember> =
        getAllProjectMemberPort.findAllByProjectId(projectId, offset, limit)

}