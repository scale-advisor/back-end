package org.scaleadvisor.backend.project.infrastructure.storage

import org.scaleadvisor.backend.global.exception.model.InternalServerException
import org.scaleadvisor.backend.global.util.FileUtil
import org.scaleadvisor.backend.project.application.port.repository.file.DownloadFilePort
import org.scaleadvisor.backend.project.application.port.repository.file.UploadFilePort
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.core.sync.ResponseTransformer
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Utilities
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.GetUrlRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest

@Component
@Profile("prod")
private class FileS3Adapter(
    private val s3Client: S3Client,
    private val s3Utilities: S3Utilities,
) : UploadFilePort, DownloadFilePort {
    @Value("\${spring.cloud.aws.s3.bucket}")
    private val bucket: String? = null

    override fun upload(
        projectId: ProjectId,
        file: MultipartFile,
        path: String
    ): String {
        FileUtil.fileValidator(file)
        FileUtil.extensionValidator(file)

        try {
            val putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(path)
                .contentType(file.contentType)
                .contentLength(file.size)
                .build()

            s3Client.putObject(
                putObjectRequest,
                RequestBody.fromInputStream(
                    file.inputStream,
                    file.size
                )
            )

        } catch (e: Exception) {
            throw InternalServerException("Fail to upload file to S3 bucket: $bucket")
        }

        val urlRequest = GetUrlRequest.builder()
            .bucket(bucket)
            .key(path)
            .build()

        return s3Utilities.getUrl(urlRequest).toExternalForm()
    }

    override fun download(
        projectId: ProjectId,
        path: String
    ): ByteArray {
        val getRequest = GetObjectRequest.builder()
            .bucket(bucket)
            .key(path)
            .build()

        val response = s3Client.getObject(
            getRequest,
            ResponseTransformer.toBytes()
        )
        return response.asByteArray()
    }

}