package org.scaleadvisor.backend.project.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.project.api.request.CreateProjectRequest
import org.scaleadvisor.backend.project.api.response.CreateProjectResponse
import org.springframework.http.HttpStatus
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
    fun create(request: CreateProjectRequest): CreateProjectResponse
}