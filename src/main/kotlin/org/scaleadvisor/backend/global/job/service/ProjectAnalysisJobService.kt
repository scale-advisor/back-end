package org.scaleadvisor.backend.global.job.service

import org.scaleadvisor.backend.global.job.AnalysisJob
import org.scaleadvisor.backend.global.job.JobStatus
import org.scaleadvisor.backend.project.application.port.usecase.project.AnalyzeProjectUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.task.TaskExecutor
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class ProjectAnalysisJobService(
    private val analyzeProjectUseCase: AnalyzeProjectUseCase,
    @Qualifier("analysisJobExecutor") private val executor: TaskExecutor
) {
    private val store = ConcurrentHashMap<String, AnalysisJob>()

    /** Job 등록 → jobId 반환 */
    fun enqueue(projectVersion: ProjectVersion): String {
        val jobId = UUID.randomUUID().toString()
        val job   = AnalysisJob(jobId, projectVersion)

        store[jobId] = job
        executor.execute { run(job) }
        return jobId
    }

    /** 상태 조회 */
    fun get(jobId: String): AnalysisJob? = store[jobId]

    /** 내부 실행 */
    private fun run(job: AnalysisJob) {
        job.status    = JobStatus.RUNNING
        job.startedAt = System.currentTimeMillis()

        try {
            val result = analyzeProjectUseCase(job.projectVersion)
            job.result      = result
            job.status      = JobStatus.SUCCESS
            job.completedAt = System.currentTimeMillis()
        } catch (ex: Exception) {
            job.status       = JobStatus.FAILED
            job.errorMessage = ex.message
            job.completedAt  = System.currentTimeMillis()
        }
    }
}

