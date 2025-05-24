package org.scaleadvisor.backend.project.application.service.member

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.member.DeleteProjectMemberPort
import org.scaleadvisor.backend.project.application.port.usecase.member.DeleteMemberUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.GetProjectUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service

@Service
private class DeleteMemberService(
    private val deleteProjectMemberPort: DeleteProjectMemberPort,
    private val getProjectUseCase: GetProjectUseCase
): DeleteMemberUseCase {
    override fun delete(email: String, projectId: Long) {
        getProjectUseCase.find(ProjectId.of(projectId))
            ?: throw NotFoundException("프로젝트 $projectId 가 존재하지 않습니다.")

        deleteProjectMemberPort.delete(email, projectId)
    }
}