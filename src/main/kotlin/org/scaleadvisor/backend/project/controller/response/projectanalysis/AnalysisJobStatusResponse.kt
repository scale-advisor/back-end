package org.scaleadvisor.backend.project.controller.response.projectanalysis

import org.scaleadvisor.backend.project.infrastructure.job.JobStatus

data class AnalysisJobStatusResponse(
    val status: JobStatus,
    val result: Any? = null,
    val error: String? = null
)
