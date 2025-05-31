package org.scaleadvisor.backend.global.job.dto

import org.scaleadvisor.backend.global.job.JobStatus

data class AnalysisJobStatusResponse(
    val status: JobStatus,
    val result: Any? = null,
    val error: String? = null
)
