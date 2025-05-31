package org.scaleadvisor.backend.global.job

import org.scaleadvisor.backend.project.domain.ProjectVersion

data class AnalysisJob(
    val id: String,
    val projectVersion: ProjectVersion,
    @Volatile var status: JobStatus = JobStatus.QUEUED,
    @Volatile var result: Any? = null,
    @Volatile var errorMessage: String? = null,
    @Volatile var startedAt: Long? = null,
    @Volatile var completedAt: Long? = null
)

enum class JobStatus { QUEUED, RUNNING, SUCCESS, FAILED }