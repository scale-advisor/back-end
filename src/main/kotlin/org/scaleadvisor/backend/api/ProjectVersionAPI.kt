package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.controller.request.version.CreateProjectVersionRequest
import org.scaleadvisor.backend.project.controller.response.version.CreateProjectVersionResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Project Version", description = "프로젝트 버전 관리 API")
@RequestMapping("projects/{projectId}/versions", produces = ["application/json;charset=utf-8"])
interface ProjectVersionAPI {
    @Operation(
        summary = "Project 버전 생성",
        description = "Project minor 버전 추가 및 요구사항, 단위 프로세스 저장",
        parameters = [
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
        @PathVariable projectId: Long,
        @RequestParam versionNumber: String,
        @RequestBody request: CreateProjectVersionRequest,
    ): SuccessResponse<CreateProjectVersionResponse>

    @Operation(
        summary = "Project 버전 삭제",
        description = "Project 버전 삭제 API. versionNumber ex> 1.0, 2.1",
        parameters  = [
            Parameter(
                name = "projectId",
                required = true,
                `in` = ParameterIn.PATH
            )
        ]
    )
    @DeleteMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable("projectId") projectId: Long,
        @RequestParam versionNumber: String
    )
}