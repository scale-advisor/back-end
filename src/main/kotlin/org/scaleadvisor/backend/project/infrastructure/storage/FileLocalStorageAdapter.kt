package org.scaleadvisor.backend.project.infrastructure.storage

import jakarta.annotation.PostConstruct
import org.scaleadvisor.backend.project.application.port.repository.file.UploadFilePort
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Component
@Profile("local")
private class FileLocalStorageAdapter(
) : UploadFilePort {
    @Value("\${storage.location}")
    private val location: String = ".storage"

    private lateinit var rootLocation: Path

    @PostConstruct
    fun init() {
        rootLocation = Paths.get(location).toAbsolutePath().normalize()
        Files.createDirectories(rootLocation)
    }

    override fun upload(
        projectId: ProjectId,
        file: MultipartFile,
        path: String
    ): String {

        fileValidator(file)
        extensionValidator(file)

        val destinationFile = rootLocation.resolve(path)

        Files.createDirectories(destinationFile.parent)

        file.inputStream.use { input ->
            Files.copy(input, destinationFile)
        }
        // 상대경로 리턴
        return destinationFile.toString()
    }


    private fun fileValidator(file: MultipartFile) {
        if (file.isEmpty || Objects.isNull(file.originalFilename)) {
//            throw BusinessException.FileEmptyException
        }
    }

    private fun extensionValidator(file: MultipartFile) {
        val contentType = file.getContentType()

        if (ObjectUtils.isEmpty(contentType) || (!contentType!!.contains("image/jpeg") && !contentType.contains(
                "image/png"
            ) && !contentType.contains("image/jpg"))
        ) {
//            throw BusinessException.InvalidFileExtensionException
        }
    }
}