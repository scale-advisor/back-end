package org.scaleadvisor.backend.project.infrastructure.storage

import jakarta.annotation.PostConstruct
import org.scaleadvisor.backend.global.util.FileUtil
import org.scaleadvisor.backend.project.application.port.repository.file.DownloadFilePort
import org.scaleadvisor.backend.project.application.port.repository.file.RemoveFilePort
import org.scaleadvisor.backend.project.application.port.repository.file.UploadFilePort
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Component
@Profile("local")
private class FileLocalStorageAdapter(
) : UploadFilePort, DownloadFilePort, RemoveFilePort {
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

        FileUtil.fileValidator(file)
        FileUtil.extensionValidator(file)

        val destinationFile = rootLocation.resolve(path)

        Files.createDirectories(destinationFile.parent)

        file.inputStream.use { input ->
            Files.copy(input, destinationFile)
        }
        // 상대경로 리턴
        return destinationFile.toString()
    }

    override fun download(path: String): ByteArray {
        return Files.readAllBytes(rootLocation.resolve(path))
    }

    override fun remove(projectId: ProjectId, projectVersion: ProjectVersion) {
        val projectDir = Paths.get(location, projectId.toString(), projectVersion.toString())
        if (Files.notExists(projectDir)) {
            return
        }
        try {
            Files.walk(projectDir)
                .sorted(Comparator.reverseOrder())
                .forEach { path ->
                    Files.deleteIfExists(path)
                }
        } catch (e: IOException) {
            throw RuntimeException("Failed to delete local files for project '${projectId}' and version '${projectVersion}'", e)
        }
    }

    override fun removeAll(projectId: ProjectId) {
        val projectDir = Paths.get(location, projectId.toString())
        if (Files.notExists(projectDir)) {
            return
        }

        try {
            Files.walk(projectDir)
                .sorted(Comparator.reverseOrder())
                .forEach { path ->
                    Files.deleteIfExists(path)
                }
        } catch (e: IOException) {
            throw RuntimeException("Failed to delete local files for project '$projectId'", e)
        }
    }

}