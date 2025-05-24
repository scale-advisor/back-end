package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.controller.request.CreateProjectOptionRequest
import org.scaleadvisor.backend.project.controller.request.UpdateProjectOptionRequest
import org.scaleadvisor.backend.project.controller.response.GetProjectOptionResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Project Option", description = "Project Option API")
@RequestMapping("projects/{projectId}/options", produces = ["application/json;charset=utf-8"])
interface ProjectOptionAPI {

    @Operation(
        summary = "Project Option 생성",
        description = "규모 분석을 위한 프로젝트 옵션(project factor, project language) 정보 생성",
        parameters  = [
            Parameter(
                name = "projectId",
                required = true,
                `in` = ParameterIn.PATH
            )
        ]
    )
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @PathVariable("projectId") projectId: Long,
        @RequestBody request: CreateProjectOptionRequest
    )

    @Operation(
        summary = "Project Option 조회",
        description = "규모 분석을 위한 프로젝트 옵션(project factor, project language) 정보 조회",
        parameters = [
            Parameter(
                name = "projectId",
                required = true,
                `in` = ParameterIn.PATH
            )
        ]
    )
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun find(
        @PathVariable("projectId") projectId: Long
    ): SuccessResponse<GetProjectOptionResponse>

    @Operation(
        summary = "Project Option 수정",
        description = "규모 분석을 위한 프로젝트 옵션(project factor, project language) 정보 수정",
        parameters = [
            Parameter(
                name = "projectId",
                required = true,
                `in` = ParameterIn.PATH
            )
        ]
    )
    @PatchMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(
        @PathVariable("projectId") projectId: Long,
        @RequestBody request: UpdateProjectOptionRequest
    )

}