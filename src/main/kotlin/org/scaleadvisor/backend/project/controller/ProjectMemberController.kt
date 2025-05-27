package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.ProjectMemberAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.controller.request.member.UpdateMemberStateRequest
import org.scaleadvisor.backend.project.controller.request.member.UpdateMemberStateResponse
import org.scaleadvisor.backend.project.application.port.usecase.member.DeleteMemberUseCase
import org.scaleadvisor.backend.project.controller.request.member.UpdateMemberRoleRequest
import org.scaleadvisor.backend.project.controller.response.member.GetAllProjectMemberResponse
import org.scaleadvisor.backend.project.controller.response.member.UpdateMemberRoleResponse
import org.scaleadvisor.backend.project.application.port.usecase.member.GetAllProjectMemberUseCase
import org.scaleadvisor.backend.project.application.port.usecase.member.UpdateMemberRoleUseCase
import org.scaleadvisor.backend.project.application.port.usecase.member.UpdateMemberStateUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.GetProjectUseCase
import org.scaleadvisor.backend.project.controller.request.member.DeleteMemberRequest
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController


@RestController
private class ProjectMemberController(
    private val getAllProjectMemberUseCase: GetAllProjectMemberUseCase,
    private val updateMemberRoleUseCase: UpdateMemberRoleUseCase,
    private val updateMemberStateUseCase: UpdateMemberStateUseCase,
    private val getProjectUseCase: GetProjectUseCase,
    private val deleteMemberUseCase: DeleteMemberUseCase
) : ProjectMemberAPI {

    override fun findAll(
        projectId: Long
    ): SuccessResponse<GetAllProjectMemberResponse> {

        val members = getAllProjectMemberUseCase
            .findAllByProjectId(ProjectId.from(projectId))

        return SuccessResponse.from(
            GetAllProjectMemberResponse.from(members)
        )
    }

    override fun updateMemberRole(
        projectId: Long,
        request: UpdateMemberRoleRequest
    ): SuccessResponse<UpdateMemberRoleResponse> {
        val updated = updateMemberRoleUseCase.update(
            UpdateMemberRoleUseCase.UpdateMemberRoleCommand(
                email = request.email,
                projectId = ProjectId.from(projectId),
                newRole = request.newRole
            )
        ) ?: throw NotFoundException("해당 멤버는 잘못된 멤버입니다.")

        return SuccessResponse.from(
            UpdateMemberRoleResponse.from(updated)
        )
    }

    override fun updateMemberState(
        projectId: Long,
        request: UpdateMemberStateRequest
    ): SuccessResponse<UpdateMemberStateResponse> {
        val updated = updateMemberStateUseCase.update(
            UpdateMemberStateUseCase.UpdateMemberStateCommand(
                email = request.email,
                projectId = ProjectId.from(projectId),
                newState = request.newState
            )
        ) ?: throw NotFoundException("해당 멤버는 잘못된 멤버입니다.")

        return SuccessResponse.from(
            UpdateMemberStateResponse.from(updated)
        )
    }

    override fun delete(projectId: Long, request: DeleteMemberRequest) {
        getProjectUseCase.find(ProjectId.from(projectId))
            ?: throw NotFoundException("해당 프로젝트는 존재하지 않습니다.")

        deleteMemberUseCase.delete(request.email, projectId)
    }

    override fun exitProject(projectId: Long) {
        getProjectUseCase.find(ProjectId.from(projectId))
            ?: throw NotFoundException("해당 프로젝트는 존재하지 않습니다.")

        deleteMemberUseCase.delete(projectId)
    }
}