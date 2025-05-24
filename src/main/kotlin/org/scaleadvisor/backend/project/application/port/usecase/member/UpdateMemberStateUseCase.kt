package org.scaleadvisor.backend.project.application.port.usecase.member

import org.scaleadvisor.backend.project.domain.ProjectMember
import org.scaleadvisor.backend.project.domain.enum.MemberState
import org.scaleadvisor.backend.project.domain.id.ProjectId

fun interface UpdateMemberStateUseCase {
    data class UpdateMemberStateCommand(
        val email: String,
        val projectId: ProjectId,
        val newState: MemberState
    )

    fun update(command: UpdateMemberStateCommand): ProjectMember?
}