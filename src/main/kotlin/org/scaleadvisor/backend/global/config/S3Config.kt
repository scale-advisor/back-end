package org.scaleadvisor.backend.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.auth.credentials.AwsCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Utilities

@Configuration
@Profile("prod")
class S3Config {

    @Value("\${spring.cloud.aws.credentials.access-key}")
    private val accessKey: String? = null

    @Value("\${spring.cloud.aws.credentials.secret-key}")
    private val secretKey: String? = null

    @Value("\${spring.cloud.aws.region.static}")
    private val region: String? = null

    @Bean
    fun s3Client(): S3Client? {
        return S3Client.builder()
            .credentialsProvider { this.awsCredentials() }
            .region(Region.of(region))
            .build()
    }

    @Bean
    fun s3Utilities(): S3Utilities =
        S3Utilities.builder()
            .region(Region.of(region))
            .build()

    private fun awsCredentials(): AwsCredentials {
        return object : AwsCredentials {
            override fun accessKeyId(): String? {
                return accessKey
            }

            override fun secretAccessKey(): String? {
                return secretKey
            }
        }
    }
}