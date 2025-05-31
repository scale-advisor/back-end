package org.scaleadvisor.backend.global.job.service

import org.scaleadvisor.backend.global.job.AnalysisJob
import org.scaleadvisor.backend.global.job.JobStatus
import org.scaleadvisor.backend.project.application.port.usecase.project.AnalyzeProjectUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.task.TaskExecutor
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProjectAnalysisJobService(
    private val analyzeProjectUseCase: AnalyzeProjectUseCase,
    private val redisTemplate: RedisTemplate<String, AnalysisJob>,
    @Qualifier("analysisJobExecutor") private val executor: TaskExecutor
) {
    companion object {
        private const val REDIS_JOB_HASH = "project:analysis:jobs"
    }

    fun enqueue(projectVersion: ProjectVersion): String {
        val jobId = UUID.randomUUID().toString()
        val job = AnalysisJob(jobId, projectVersion)

        redisTemplate.opsForHash<String, AnalysisJob>().put(REDIS_JOB_HASH, jobId, job)
        executor.execute { run(job) }

        return jobId
    }

    fun get(jobId: String): AnalysisJob? {
        val job = redisTemplate.opsForHash<String, AnalysisJob>()
            .get(REDIS_JOB_HASH, jobId)

        if (job != null) {
            if (job.status == JobStatus.SUCCESS || job.status == JobStatus.FAILED) {
                redisTemplate.opsForHash<String, AnalysisJob>()
                    .delete(REDIS_JOB_HASH, jobId)
                return job
            }
        }

        return job
    }

    private fun run(job: AnalysisJob) {
        // ① 실행 직전 상태 저장
        job.status = JobStatus.RUNNING
        job.startedAt = System.currentTimeMillis()
        saveJob(job)

        try {
            // 실제 분석 로직 수행
            val result = analyzeProjectUseCase(job.projectVersion)

            // ② 성공 시 상태 업데이트
            job.result = result
            job.status = JobStatus.SUCCESS
            job.completedAt = System.currentTimeMillis()
            saveJob(job)
        } catch (ex: Exception) {
            // ③ 실패 시 상태 업데이트
            job.status = JobStatus.FAILED
            job.errorMessage = ex.message
            job.completedAt = System.currentTimeMillis()
            saveJob(job)
        }
    }

    /** Redis에 job 객체를 저장(업데이트) */
    private fun saveJob(job: AnalysisJob) {
        redisTemplate.opsForHash<String, AnalysisJob>()
            .put(REDIS_JOB_HASH, job.jobId, job)
    }
}

