package org.scaleadvisor.backend.project.application.service.member

import org.scaleadvisor.backend.project.application.port.repository.member.UpdateMemberRolePort
import org.scaleadvisor.backend.project.application.port.usecase.member.UpdateMemberRoleUseCase
import org.scaleadvisor.backend.project.domain.ProjectMember
import org.springframework.stereotype.Service

@Service
class UpdateMemberRoleService(
    private val updateMemberRolePort: UpdateMemberRolePort
): UpdateMemberRoleUseCase {

    override fun update(command: UpdateMemberRoleUseCase.UpdateMemberRoleCommand
    ): ProjectMember? =
        updateMemberRolePort.updateRole(
            command.email,
            command.projectId,
            command.newRole
        )
}