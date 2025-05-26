package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.controller.request.member.DeleteMemberRequest
import org.scaleadvisor.backend.project.controller.request.member.UpdateMemberRoleRequest
import org.scaleadvisor.backend.project.controller.request.member.UpdateMemberStateRequest
import org.scaleadvisor.backend.project.controller.request.member.UpdateMemberStateResponse
import org.scaleadvisor.backend.project.controller.response.member.GetAllProjectMemberResponse
import org.scaleadvisor.backend.project.controller.response.member.UpdateMemberRoleResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Project Member", description = "프로젝트 멤버 관리 API")
@RequestMapping("projects", produces = ["application/json;charset=utf-8"])
interface ProjectMemberAPI {

    @GetMapping("/{projectId}/users")
    @ResponseStatus(HttpStatus.OK)
    fun findAll(@PathVariable("projectId") projectId: Long
    ): SuccessResponse<GetAllProjectMemberResponse>

    @DeleteMapping("/{projectId}/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("projectId") projectId: Long,
               @RequestBody request: DeleteMemberRequest
    )

    @PatchMapping("/{projectId}/users/role")
    @ResponseStatus(HttpStatus.OK)
    fun updateMemberRole(@PathVariable projectId: Long,
                         @RequestBody request: UpdateMemberRoleRequest
    ): SuccessResponse<UpdateMemberRoleResponse>

    @PatchMapping("/{projectId}/users/state")
    @ResponseStatus(HttpStatus.OK)
    fun updateMemberState(@PathVariable projectId: Long,
                         @RequestBody request: UpdateMemberStateRequest
    ): SuccessResponse<UpdateMemberStateResponse>
}