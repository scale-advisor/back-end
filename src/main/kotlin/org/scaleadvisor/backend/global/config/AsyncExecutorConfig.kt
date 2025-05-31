package org.scaleadvisor.backend.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
class AsyncExecutorConfig {
    @Bean("analysisJobExecutor")          // ★ 이름 부여
    fun analysisJobExecutor(): ThreadPoolTaskExecutor {
        return ThreadPoolTaskExecutor().apply {
            corePoolSize  = 4
            maxPoolSize   = 8
            queueCapacity = 100
            setThreadNamePrefix("analysis-")
            initialize()
        }
    }
}