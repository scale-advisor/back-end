package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.ProjectAnalysisAPI
import org.scaleadvisor.backend.project.application.port.usecase.GetJobUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.AnalyzeProjectUseCase
import org.scaleadvisor.backend.project.controller.response.projectanalysis.AnalysisJobStatusResponse
import org.scaleadvisor.backend.project.controller.response.projectanalysis.JobIdResponse
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.infrastructure.job.AnalysisStage
import org.scaleadvisor.backend.project.infrastructure.job.JobStatus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectAnalysisController(
    private val analyzeProjectUseCase: AnalyzeProjectUseCase,
    private val getJobUseCase: GetJobUseCase
): ProjectAnalysisAPI {

    override fun analyze(
        projectId: Long,
        versionNumber: String
    ): JobIdResponse {
        // TODO: TO REMOVE
        if (projectId == 2025060423390587487) {
            return JobIdResponse("20250604233905")
        }
        val pv = ProjectVersion.of(ProjectId.from(projectId), versionNumber)
        val onlyClassify = false
        val jobId = analyzeProjectUseCase.invoke(pv, AnalysisStage.ETL_UNIT_PROCESS, onlyClassify)
        return JobIdResponse(jobId)
    }

    override fun getJobStatus(
        @PathVariable projectId: Long,
        @PathVariable jobId: String
    ): ResponseEntity<AnalysisJobStatusResponse> {
        val job = getJobUseCase.invoke(jobId)
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

    override fun reClassify(projectId: Long, versionNumber: String): JobIdResponse {
        // TODO: TO REMOVE
        if (projectId == 2025060423390587487) {
            return JobIdResponse("20250604233905")
        }
        val pv = ProjectVersion.of(ProjectId.from(projectId), versionNumber)
        val onlyClassify = true
        val jobId = analyzeProjectUseCase.invoke(pv, AnalysisStage.CLASSIFY_FUNCTION, onlyClassify)
        return JobIdResponse(jobId)
    }
}