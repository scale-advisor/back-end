package org.scaleadvisor.backend.global.job

import org.scaleadvisor.backend.project.domain.ProjectVersion
import java.io.Serializable

data class AnalysisJob(
    val jobId: String,
    val projectVersion: ProjectVersion
) : Serializable {
    var status: JobStatus = JobStatus.QUEUED
    var startedAt: Long? = null
    var completedAt: Long? = null
    var result: Any? = null
    var errorMessage: String? = null
}

enum class JobStatus { QUEUED, RUNNING, SUCCESS, FAILED }