package org.scaleadvisor.backend.project.infrastructure.job

import org.scaleadvisor.backend.project.domain.ProjectVersion
import java.io.Serializable

data class AnalysisJob(
    val jobId: String,
    val projectVersion: ProjectVersion,
    var retryCount: Int = 0,
    val maxRetries: Int = 5,
    var stage: AnalysisStage = AnalysisStage.ETL_UNIT_PROCESS,
    var onlyClassify: Boolean = false
) : Serializable {
    var status: JobStatus = JobStatus.QUEUED
    var startedAt: Long? = null
    var completedAt: Long? = null
    var result: Any? = null
    var errorMessage: String? = null
}

enum class JobStatus { QUEUED, RUNNING, SUCCESS, FAILED }

enum class AnalysisStage {
    ETL_UNIT_PROCESS,
    VALIDATE_UNIT_PROCESS,
    CLASSIFY_FUNCTION,
    COMPUTE_ADJUSTMENT,
    DONE
}