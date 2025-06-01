package org.scaleadvisor.backend.global.job.service

import org.scaleadvisor.backend.global.job.AnalysisJob
import org.scaleadvisor.backend.global.job.JobStatus
import org.scaleadvisor.backend.project.application.port.usecase.project.AnalyzeProjectUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.task.TaskExecutor
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.*

@Service
class ProjectAnalysisJobService(
    private val analyzeProjectUseCase: AnalyzeProjectUseCase,
    private val redisTemplate: RedisTemplate<String, AnalysisJob>,
    @Qualifier("analysisJobExecutor") private val executor: TaskExecutor
) {
    companion object {
        private const val JOB_KEY_PREFIX = "project:analysis:job:"
        private val JOB_TTL: Duration = Duration.ofMinutes(10)
    }

    fun enqueue(projectVersion: ProjectVersion): String {
        val jobId = UUID.randomUUID().toString()
        val job = AnalysisJob(jobId, projectVersion)

        val redisKey = JOB_KEY_PREFIX + jobId
        redisTemplate.opsForValue().set(redisKey, job, JOB_TTL)

        executor.execute { run(job) }

        return jobId
    }

    fun get(jobId: String): AnalysisJob? {
        val redisKey = JOB_KEY_PREFIX + jobId
        return redisTemplate.opsForValue().get(redisKey)
    }

    private fun run(job: AnalysisJob) {
        val redisKey = JOB_KEY_PREFIX + job.jobId

        job.status = JobStatus.RUNNING
        job.startedAt = System.currentTimeMillis()
        saveJob(redisKey, job)

        try {
            val result = analyzeProjectUseCase(job.projectVersion)
            job.result = result
            job.status = JobStatus.SUCCESS
            job.completedAt = System.currentTimeMillis()
            saveJob(redisKey, job)
        } catch (ex: Exception) {
            job.status = JobStatus.FAILED
            job.errorMessage = ex.message
            job.completedAt = System.currentTimeMillis()
            saveJob(redisKey, job)
        }
    }

    private fun saveJob(redisKey: String, job: AnalysisJob) {
        redisTemplate.opsForValue().set(redisKey, job, JOB_TTL)
    }
}

