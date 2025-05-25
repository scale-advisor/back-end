package org.scaleadvisor.backend.project.application.service.member

import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.project.application.port.repository.member.UpdateMemberStatePort
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsOwnerUseCase
import org.scaleadvisor.backend.project.application.port.usecase.member.UpdateMemberStateUseCase
import org.scaleadvisor.backend.project.domain.ProjectMember
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class UpdateMemberStateService(
    private val checkIsOwnerUseCase: CheckIsOwnerUseCase,
    private val updateMemberStatePort: UpdateMemberStatePort
): UpdateMemberStateUseCase{

    override fun update(command: UpdateMemberStateUseCase.UpdateMemberStateCommand
    ): ProjectMember? {
        if (!checkIsOwnerUseCase.checkIsOwner(command.projectId.toLong())) {
            throw ForbiddenException("멤버 상태를 수정할 권한이 없습니다.")
        }

        return updateMemberStatePort.updateState(
            command.email,
            command.projectId,
            command.newState
        )
    }
}
