package org.scaleadvisor.backend.global.job.controller

import org.scaleadvisor.backend.api.ProjectAnalysisAPI
import org.scaleadvisor.backend.global.job.JobStatus
import org.scaleadvisor.backend.global.job.dto.AnalysisJobStatusResponse
import org.scaleadvisor.backend.global.job.service.ProjectAnalysisJobService
import org.scaleadvisor.backend.project.controller.response.JobIdResponse
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectAnalysisJobController(
    private val jobService: ProjectAnalysisJobService
): ProjectAnalysisAPI {

    override fun analyze(
        projectId: Long,
        versionNumber: String
    ): JobIdResponse {
        val pv = ProjectVersion.of(ProjectId.from(projectId), versionNumber)
        val jobId = jobService.enqueue(pv)
        return JobIdResponse(jobId)
    }

    @GetMapping("projects/{projectId}/analysis/jobs/{jobId}")
    fun getJobStatus(
        @PathVariable projectId: Long,
        @PathVariable jobId: String
    ): ResponseEntity<AnalysisJobStatusResponse> {

        val job = jobService.get(jobId)
            ?: return ResponseEntity.notFound().build()

        if (job.projectVersion.projectId.toLong() != projectId)
            return ResponseEntity.notFound().build()

        return when (job.status) {
            JobStatus.QUEUED, JobStatus.RUNNING -> ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Retry-After", "3")
                .body(AnalysisJobStatusResponse(job.status))

            JobStatus.SUCCESS -> ResponseEntity.ok(
                AnalysisJobStatusResponse(
                    status = job.status,
                    result = job.result
                )
            )

            JobStatus.FAILED -> ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    AnalysisJobStatusResponse(
                        status = job.status,
                        error  = job.errorMessage
                    )
                )
        }
    }
}