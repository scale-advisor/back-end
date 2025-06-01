package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.controller.request.requirement.CreateAllRequirementCategoryRequest
import org.scaleadvisor.backend.project.controller.request.requirement.CreateAllRequirementRequest
import org.scaleadvisor.backend.project.controller.request.requirement.UpdateAllRequirementRequest
import org.scaleadvisor.backend.project.controller.response.requirement.GetAllRequirementResponse
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
    fun createAll(
        @PathVariable projectId: Long,
        @RequestParam versionNumber: String,
        @RequestBody requirementList: List<CreateAllRequirementRequest>
    )


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
    ): SuccessResponse<GetAllRequirementResponse>


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
    fun updateAll(
        @RequestBody requirementList: List<UpdateAllRequirementRequest>
    )

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
    fun deleteAll(
        @RequestBody requirementIdList: List<String>
    )

    @Operation(
        summary = "Project 요구사항 분류 등록",
        description = "보정계수에 참고할 Project 요구사항 분류 등록 API\n\n" +
                "**requirementCategoryName**\n\n" +
                "FUNCTION: 기능\n\n" +
                "INTEGRATION_COMPLEXITY: 연계복잡성\n\n" +
                "PERFORMANCE, 성능 요구수준\n\n" +
                "OPERATIONAL_COMPATIBILITY: 운영환경 호환성\n\n" +
                "SECURITY: 보안성 요구수준",
        parameters  = [
            Parameter(
                name = "projectId",
                required = true,
                `in` = ParameterIn.PATH
            )
        ]
    )
    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    fun createAllCategory(
        @PathVariable("projectId") projectId: Long,
        @RequestBody requirementCategoryList: List<CreateAllRequirementCategoryRequest>
    )
}