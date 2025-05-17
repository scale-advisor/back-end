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
            .sql("""
            SELECT user_id
              FROM user
             WHERE deleted_at IS NOT NULL
               AND deleted_at < ?
        """.trimIndent())
            .preparedStatementSetter { ps ->
                val cutoff = LocalDateTime.now().minusDays(30)
                ps.setObject(1, cutoff)
            }
            .rowMapper { rs, _ -> rs.getLong("user_id") }
            .build()

    @Bean
    fun deleteUserWriter(): JdbcBatchItemWriter<Long> =
        JdbcBatchItemWriterBuilder<Long>()
            .dataSource(dataSource)
            .sql("DELETE FROM user WHERE user_id = :userId")
            .itemSqlParameterSourceProvider { MapSqlParameterSource("userId", it) }
            .build()

    @Bean
    fun deleteUserStep(): Step =
        StepBuilder("deleteUserStep", jobRepository)
            .chunk<Long, Long>(100, transactionManager)
            .reader(deleteUserReader())
            .writer(deleteUserWriter())
            .build()

    @Bean
    fun deleteUserJob(): Job =
        JobBuilder("deleteUserJob", jobRepository)
            .start(deleteUserStep())
            .build()
}