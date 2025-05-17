package org.scaleadvisor.backend.global.job

import org.quartz.JobExecutionContext
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component

@Component
class DeleteBatchJob: QuartzJobBean(){
    @Autowired
    private lateinit var jobLauncher: JobLauncher
    @Autowired
    private lateinit var deleteUserJob: Job

    override fun executeInternal(ctx: JobExecutionContext) {
        val params = JobParametersBuilder()
            .addLong("runAt", System.currentTimeMillis())
            .toJobParameters()
        jobLauncher.run(deleteUserJob, params)
    }
}