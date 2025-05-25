package org.scaleadvisor.backend.project.application.service.member

import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.project.application.port.repository.member.UpdateMemberRolePort
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsOwnerUseCase
import org.scaleadvisor.backend.project.application.port.usecase.member.UpdateMemberRoleUseCase
import org.scaleadvisor.backend.project.domain.ProjectMember
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class UpdateMemberRoleService(
    private val checkIsOwnerUseCase: CheckIsOwnerUseCase,
    private val updateMemberRolePort: UpdateMemberRolePort
): UpdateMemberRoleUseCase {

    override fun update(command: UpdateMemberRoleUseCase.UpdateMemberRoleCommand
    ): ProjectMember? {
        if (!checkIsOwnerUseCase.checkIsOwner(command.projectId.toLong())) {
            throw ForbiddenException("멤버 역할을 수정할 권한이 없습니다.")
        }

        return updateMemberRolePort.updateRole(
            command.email,
            command.projectId,
            command.newRole
        )
    }



}