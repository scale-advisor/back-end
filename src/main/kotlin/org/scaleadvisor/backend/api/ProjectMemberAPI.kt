package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.api.response.GetAllProjectMemberResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "ProjectMember", description = "ProjectMember API")
@RequestMapping("projects", produces = ["application/json;charset=utf-8"])
interface ProjectMemberAPI {

    @GetMapping("/{projectId}/users")
    @ResponseStatus(HttpStatus.OK)
    fun findAll(@PathVariable("projectId") projectId: Long,
                @RequestParam(name = "offset", defaultValue = "0") offset: Int,
                @RequestParam(name = "limit", defaultValue = "20") limit: Int
    ): SuccessResponse<GetAllProjectMemberResponse>
}