package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.controller.response.unitprocess.GetAllUnitProcessResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Project Unit Process", description = "프로젝트 단위 프로세스 관리 API")
@RequestMapping("projects/{projectId}/unit-processes", produces = ["application/json;charset=utf-8"])
interface ProjectUnitProcessAPI {
    @Operation(
        summary = "Project 요구사항 조회",
        description = "Project 버전 별 요구사항 조회 API",
        parameters  = [
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
        @PathVariable projectId: Long,
        @RequestParam versionNumber: String,
    ): SuccessResponse<GetAllUnitProcessResponse>

}