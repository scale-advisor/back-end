package org.scaleadvisor.backend.project.application.service.member

import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.global.exception.model.UnauthorizedException
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.scaleadvisor.backend.project.application.port.repository.member.DeleteProjectMemberPort
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsOwnerUseCase
import org.scaleadvisor.backend.project.application.port.usecase.member.DeleteMemberUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.GetProjectUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteMemberService(
    private val deleteProjectMemberPort: DeleteProjectMemberPort,
    private val getProjectUseCase: GetProjectUseCase,
    private val checkIsOwnerUseCase: CheckIsOwnerUseCase
): DeleteMemberUseCase {
    override fun delete(email: String, projectId: Long) {
        if (!checkIsOwnerUseCase.checkIsOwner(projectId)) {
            throw ForbiddenException("프로젝트 요소를 수정할 권한이 없습니다.")
        }

        getProjectUseCase.find(ProjectId.from(projectId))
            ?: throw NotFoundException("프로젝트 $projectId 가 존재하지 않습니다.")

        deleteProjectMemberPort.delete(email, projectId)
    }

    override fun delete(projectId: Long) {
        val currentUser = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw UnauthorizedException("만료 되거나 잘못된 인증 입니다.")

        deleteProjectMemberPort.delete(currentUser, ProjectId.from(projectId))
    }
}