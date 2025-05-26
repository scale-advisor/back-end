package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.controller.request.project.CreateProjectRequest
import org.scaleadvisor.backend.project.controller.request.project.UpdateProjectRequest
import org.scaleadvisor.backend.project.controller.response.project.CreateProjectResponse
import org.scaleadvisor.backend.project.controller.response.project.GetAllProjectResponse
import org.scaleadvisor.backend.project.controller.response.project.UpdateProjectResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Project", description = "프로젝트 기본 관리 API")
@RequestMapping("projects", produces = ["application/json;charset=utf-8"])
interface ProjectAPI {

    @Operation(
        summary = "Project 생성",
        description = "규모 분석을 위한 새 프로젝트 생성"
    )
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateProjectRequest): SuccessResponse<CreateProjectResponse>

    @Operation(
        summary = "Project 목록 조회",
        description = "로그인한 회원이 생성한/참여하고 있는 프로젝트 목록 조회"
    )
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): SuccessResponse<GetAllProjectResponse>

    @Operation(
        summary = "Project 기본 정보 수정",
        description = "프로젝트 이름, 설명 수정"
    )
    @PutMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    fun update(
        @PathVariable("projectId") projectId: Long,
        @RequestBody request: UpdateProjectRequest
    ): SuccessResponse<UpdateProjectResponse>

    @Operation(
        summary = "Project 삭제",
        description = "프로젝트 삭제"
    )
    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable("projectId") projectId: Long,
    )
}