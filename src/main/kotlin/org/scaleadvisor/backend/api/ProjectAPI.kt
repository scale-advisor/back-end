package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.api.request.CreateProjectRequest
import org.scaleadvisor.backend.project.api.response.CreateProjectResponse
import org.scaleadvisor.backend.project.api.response.FindAllProjectResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Tag(name = "Project", description = "Project API")
@RequestMapping("projects", produces = ["application/json;charset=utf-8"])
interface ProjectAPI {

    @Operation(
        summary = "Project 생성",
        description = "규모 분석을 위한 새 프로젝트 생성"
    )
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(request: CreateProjectRequest): SuccessResponse<CreateProjectResponse>

    @Operation(
        summary = "Project 목록 조회",
        description = "로그인한 회원이 생성한/참여하고 있는 프로젝트 목록 조회"
    )
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): SuccessResponse<FindAllProjectResponse>

}