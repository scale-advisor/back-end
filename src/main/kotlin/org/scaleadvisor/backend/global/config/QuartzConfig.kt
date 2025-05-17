package org.scaleadvisor.backend.global.config

import org.quartz.*
import org.scaleadvisor.backend.global.job.DeleteBatchJob
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class QuartzConfig {
    @Bean
    fun deleteJobDetail(): JobDetail =
        JobBuilder.newJob(DeleteBatchJob::class.java)
            .withIdentity("deleteUserJob")
            .storeDurably()
            .build()

    @Bean
    fun deleteJobTrigger(jobDetail: JobDetail): Trigger =
        TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity("deleteUserTrigger")
            .withSchedule(
                CronScheduleBuilder
                    .dailyAtHourAndMinute(0, 0)
                    .inTimeZone(TimeZone.getTimeZone("Asia/Seoul"))
            )
            .build()
}