package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.controller.request.adjustmentfactor.UpdateAdjustmentFactorRequest
import org.scaleadvisor.backend.project.controller.response.adjustmentfactor.GetProjectAdjustmentFactorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Project Adjustment Factor", description = "프로젝트 보정계수 관리 API")
@RequestMapping("projects/{projectId}/adjustment-factors", produces = ["application/json;charset=utf-8"])
interface ProjectAdjustmentFactorAPI {
    @Operation(
        summary = "Project 보정계수 조회",
        description = "Project 보정계수 조회 API",
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
    fun findAll(
        @PathVariable("projectId") projectId: Long,
        @RequestParam versionNumber: String
    ): SuccessResponse<GetProjectAdjustmentFactorResponse>

    @Operation(
        summary = "Project 보정계수 수정",
        description = "Project 보정계수 수정 API",
        parameters  = [
            Parameter(
                name = "projectId",
                required = true,
                `in` = ParameterIn.PATH
            )
        ]
    )

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateAll(
        @PathVariable("projectId") projectId: Long,
        @RequestBody request: UpdateAdjustmentFactorRequest
    )
}