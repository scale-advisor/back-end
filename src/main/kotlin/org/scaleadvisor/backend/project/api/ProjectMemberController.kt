package org.scaleadvisor.backend.project.api

import org.scaleadvisor.backend.api.ProjectMemberAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.api.response.GetAllProjectMemberResponse
import org.scaleadvisor.backend.project.application.port.usecase.member.GetAllProjectMemberUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController


@RestController
private class ProjectMemberController(
    private val getAllProjectMemberUseCase: GetAllProjectMemberUseCase
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
}