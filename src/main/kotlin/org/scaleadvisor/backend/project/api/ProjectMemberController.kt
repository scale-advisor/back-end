package org.scaleadvisor.backend.project.api

import org.scaleadvisor.backend.api.ProjectMemberAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.api.request.UpdateMemberRoleRequest
import org.scaleadvisor.backend.project.api.response.GetAllProjectMemberResponse
import org.scaleadvisor.backend.project.api.response.UpdateProjectMemberResponse
import org.scaleadvisor.backend.project.application.port.usecase.member.GetAllProjectMemberUseCase
import org.scaleadvisor.backend.project.application.port.usecase.member.UpdateMemberRoleUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController


@RestController
private class ProjectMemberController(
    private val getAllProjectMemberUseCase: GetAllProjectMemberUseCase,
    private val updateMemberRoleUseCase: UpdateMemberRoleUseCase
) : ProjectMemberAPI {

    override fun findAll(
        projectId: Long,
        offset: Int,
        limit: Int
    ): SuccessResponse<GetAllProjectMemberResponse> {

        val members = getAllProjectMemberUseCase
            .findAllByProjectId(ProjectId.of(projectId), offset, limit)

        return SuccessResponse.from(
            GetAllProjectMemberResponse.from(members)
        )
    }

    override fun updateMemberRole(
        projectId: Long,
        request: UpdateMemberRoleRequest
    ): SuccessResponse<UpdateProjectMemberResponse> {
        val updated = updateMemberRoleUseCase.update(
            UpdateMemberRoleUseCase.UpdateMemberRoleCommand(
                email = request.email,
                projectId = ProjectId.of(projectId),
                newRole = request.newRole
            )
        ) ?: throw NotFoundException("해당 멤버는 잘못된 멤버입니다.")

        return SuccessResponse.from(
            UpdateProjectMemberResponse.from(updated)
        )
    }
}