package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.api.request.CreateProjectFactorRequest
import org.scaleadvisor.backend.project.api.response.GetProjectOptionResponse
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
        @RequestBody request: CreateProjectFactorRequest
    )

    @Operation(
        summary = "Project Option 생성",
        description = "규모 분석을 위한 프로젝트 옵션(project factor, project language) 정보 생성",
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

}