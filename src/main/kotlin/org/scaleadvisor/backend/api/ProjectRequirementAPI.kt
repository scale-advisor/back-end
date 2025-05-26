package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.project.controller.request.requirement.CreateRequirementRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Project Requirement", description = "프로젝트 요구사항 관리 API")
@RequestMapping("projects/{projectId}/requirements", produces = ["application/json;charset=utf-8"])
interface ProjectRequirementAPI {
    @Operation(
        summary = "Project 요구사항 저장",
        description = "Project 요구사항 저장 API",
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
        @RequestBody requirementList: List<CreateRequirementRequest>
    )
}