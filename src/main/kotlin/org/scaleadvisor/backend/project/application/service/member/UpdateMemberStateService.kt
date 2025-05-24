package org.scaleadvisor.backend.project.application.service.member

import org.scaleadvisor.backend.project.application.port.repository.member.UpdateMemberStatePort
import org.scaleadvisor.backend.project.application.port.usecase.member.UpdateMemberStateUseCase
import org.scaleadvisor.backend.project.domain.ProjectMember
import org.springframework.stereotype.Service

@Service
private class UpdateMemberStateService(
    private val updateMemberStatePort: UpdateMemberStatePort
): UpdateMemberStateUseCase{

    override fun update(command: UpdateMemberStateUseCase.UpdateMemberStateCommand
    ): ProjectMember? =
        updateMemberStatePort.updateState(
            command.email,
            command.projectId,
            command.newState
        )
}