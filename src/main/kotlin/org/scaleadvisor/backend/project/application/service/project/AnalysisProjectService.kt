package org.scaleadvisor.backend.project.application.service.project

import org.scaleadvisor.backend.project.infrastructure.job.AnalysisJob
import org.scaleadvisor.backend.project.infrastructure.job.AnalysisStage
import org.scaleadvisor.backend.project.infrastructure.job.JobStatus
import org.scaleadvisor.backend.project.application.port.usecase.GetJobUseCase
import org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor.AnalyzeAdjustmentFactorLevelUseCase
import org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor.CreateAdjustmentFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.AnalyzeProjectUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.ClassifyUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.ETLUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.UpdateUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.ValidateUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.AdjustmentFactor
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.task.TaskExecutor
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.*

@Service
private class AnalysisProjectService(
    private val etlUnitProcess: ETLUnitProcessUseCase,
    private val validateUnitProcess: ValidateUnitProcessUseCase,
    private val classifyUnitProcess: ClassifyUnitProcessUseCase,
    private val updateUnitProcessUseCase: UpdateUnitProcessUseCase,
    private val analyzeAdjustmentFactorLevel: AnalyzeAdjustmentFactorLevelUseCase,
    private val createAdjustmentFactorUseCase: CreateAdjustmentFactorUseCase,

    private val redisTemplate: RedisTemplate<String, AnalysisJob>,
    @Qualifier("analysisJobExecutor") private val executor: TaskExecutor
): AnalyzeProjectUseCase, GetJobUseCase {
    companion object {
        private const val JOB_KEY_PREFIX = "project:analysis:job:"
        private val JOB_TTL: Duration = Duration.ofMinutes(10)
    }

    override operator fun invoke(projectVersion: ProjectVersion): String {
        return enqueue(projectVersion)
    }

    override operator fun invoke(jobId: String): AnalysisJob? {
        return get(jobId)
    }

    private fun enqueue(projectVersion: ProjectVersion): String {
        val jobId = UUID.randomUUID().toString()
        val job = AnalysisJob(jobId, projectVersion)
        val redisKey = JOB_KEY_PREFIX + jobId

        redisTemplate.opsForValue().set(redisKey, job, JOB_TTL)
        executor.execute { run(job) }
        return jobId
    }

    private fun get(jobId: String): AnalysisJob? {
        val redisKey = JOB_KEY_PREFIX + jobId
        return redisTemplate.opsForValue().get(redisKey)
    }

    private fun run(job: AnalysisJob) {
        val redisKey = JOB_KEY_PREFIX + job.jobId

        job.status = JobStatus.RUNNING
        job.startedAt = System.currentTimeMillis()
        saveJob(redisKey, job)

        try {
            if (job.stage == AnalysisStage.ETL_UNIT_PROCESS) {
                etlUnitProcess.invoke(job.projectVersion)
                job.stage = AnalysisStage.VALIDATE_UNIT_PROCESS
                saveJob(redisKey, job)
            }

            if (job.stage == AnalysisStage.VALIDATE_UNIT_PROCESS) {
                validateUnitProcess.invoke(job.projectVersion)
                job.stage = AnalysisStage.CLASSIFY_FUNCTION
                saveJob(redisKey, job)
            }

            if (job.stage == AnalysisStage.CLASSIFY_FUNCTION) {
                val unitProcessList: List<UnitProcess> = classifyUnitProcess.invoke(job.projectVersion)
                updateUnitProcessUseCase.updateAll(unitProcessList)
                job.stage = AnalysisStage.COMPUTE_ADJUSTMENT
                saveJob(redisKey, job)
            }

            if (job.stage == AnalysisStage.COMPUTE_ADJUSTMENT) {
                val adjustmentFactorList: List<AdjustmentFactor> =
                    analyzeAdjustmentFactorLevel.invoke(job.projectVersion)
                createAdjustmentFactorUseCase.createAll(adjustmentFactorList)

                job.stage = AnalysisStage.DONE
                job.status = JobStatus.SUCCESS
                job.completedAt = System.currentTimeMillis()
                saveJob(redisKey, job)
                return
            }

            if (job.stage == AnalysisStage.DONE) {
                job.status = JobStatus.SUCCESS
                job.completedAt = System.currentTimeMillis()
                saveJob(redisKey, job)
            }
        } catch (ex: Exception) {
            job.status = JobStatus.FAILED
            job.errorMessage = ex.message
            job.completedAt = System.currentTimeMillis()
            saveJob(redisKey, job)

            if (job.retryCount < job.maxRetries) {
                job.retryCount += 1
                saveJob(redisKey, job)
                executor.execute { run(job) }
            }
        }
    }

    private fun saveJob(redisKey: String, job: AnalysisJob) {
        redisTemplate.opsForValue().set(redisKey, job, JOB_TTL)
    }
}

