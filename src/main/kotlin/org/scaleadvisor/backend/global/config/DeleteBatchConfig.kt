package org.scaleadvisor.backend.global.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.batch.item.support.CompositeItemWriter
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDateTime
import javax.sql.DataSource

@Configuration
@EnableBatchProcessing
class DeleteBatchConfig(
    private val dataSource: DataSource,
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager
) {
    @Bean
    fun deleteUserReader(): JdbcCursorItemReader<Long> =
        JdbcCursorItemReaderBuilder<Long>()
            .name("deleteUserReader")
            .dataSource(dataSource)
            .sql(
                """
                SELECT USER_ID
                  FROM `USER`
                 WHERE DELETED_AT IS NOT NULL
                   AND DELETED_AT < ?
                """.trimIndent()
            )
            .preparedStatementSetter { ps ->
                val cutoff = LocalDateTime.now().minusDays(30)
                ps.setObject(1, cutoff)
            }
            .rowMapper { rs, _ -> rs.getLong("USER_ID") }
            .build()

    @Bean
    fun deleteUserProjectWriter(): JdbcBatchItemWriter<Long> =
        JdbcBatchItemWriterBuilder<Long>()
            .dataSource(dataSource)
            .sql("DELETE FROM USER_PROJECT WHERE USER_ID = :userId")
            .itemSqlParameterSourceProvider { MapSqlParameterSource("userId", it) }
            .build()

    @Bean
    fun deleteUserWriter(): JdbcBatchItemWriter<Long> =
        JdbcBatchItemWriterBuilder<Long>()
            .dataSource(dataSource)
            .sql("DELETE FROM `USER` WHERE USER_ID = :userId")
            .itemSqlParameterSourceProvider { MapSqlParameterSource("userId", it) }
            .build()

    @Bean
    fun deleteUserCompositeWriter(): CompositeItemWriter<Long> =
        CompositeItemWriterBuilder<Long>()
            .delegates(listOf(deleteUserProjectWriter(), deleteUserWriter()))
            .build()

    @Bean
    fun deleteUserStep(): Step =
        StepBuilder("deleteUserStep", jobRepository)
            .chunk<Long, Long>(100, transactionManager)
            .reader(deleteUserReader())
            .writer(deleteUserCompositeWriter())
            .build()

    @Bean
    fun deleteUserJob(): Job =
        JobBuilder("deleteUserJob", jobRepository)
            .start(deleteUserStep())
            .build()
}