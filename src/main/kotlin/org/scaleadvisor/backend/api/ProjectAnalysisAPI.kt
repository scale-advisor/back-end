package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.project.controller.response.projectanalysis.AnalysisJobStatusResponse
import org.scaleadvisor.backend.project.controller.response.projectanalysis.JobIdResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Project Analysis", description = "프로젝트 분석 API")
@RequestMapping("projects/{projectId}/analysis", produces = ["application/json;charset=utf-8"])
interface ProjectAnalysisAPI {

    @Operation(
        summary = "Project 분석",
        description = "프로젝트 분석 API",
        parameters  = [
            Parameter(
                name = "projectId",
                required = true,
                `in` = ParameterIn.PATH
            )
        ]
    )
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun analyze(
        @PathVariable projectId: Long,
        @RequestParam versionNumber: String,
    ): JobIdResponse

    @Operation(
        summary = "Job 상태 조회",
        description = "프로젝트 분석 Job 상태 조회 API"
    )
    @GetMapping("jobs/{jobId}")
    fun getJobStatus(
        @PathVariable projectId: Long,
        @PathVariable jobId: String
    ): ResponseEntity<AnalysisJobStatusResponse>
}