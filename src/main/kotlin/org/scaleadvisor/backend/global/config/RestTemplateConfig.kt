package org.scaleadvisor.backend.global.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import org.scaleadvisor.backend.project.infrastructure.job.AnalysisJob
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.web.client.RestTemplate


@Configuration
class RestTemplateConfig {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()

    @Bean
    fun redisTemplate(
        connectionFactory: RedisConnectionFactory,
        objectMapper: ObjectMapper
    ): RedisTemplate<String, AnalysisJob> {
        val template = RedisTemplate<String, AnalysisJob>()
        template.setConnectionFactory(connectionFactory)

        template.keySerializer = StringRedisSerializer()

        val mapperCopy = objectMapper.copy().apply {
            setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
            findAndRegisterModules()
        }

        val valueSerializer = Jackson2JsonRedisSerializer(mapperCopy, AnalysisJob::class.java)

        template.valueSerializer = valueSerializer

        template.hashKeySerializer = StringRedisSerializer()
        template.hashValueSerializer = valueSerializer

        template.afterPropertiesSet()
        return template
    }
}