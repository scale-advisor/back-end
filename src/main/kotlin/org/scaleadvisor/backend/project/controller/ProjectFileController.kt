package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.ProjectFileAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.global.exception.model.UnauthorizedException
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.scaleadvisor.backend.project.application.port.usecase.file.CreateFileUseCase
import org.scaleadvisor.backend.project.application.port.usecase.file.DownloadFileUseCase
import org.scaleadvisor.backend.project.application.port.usecase.file.GetFileUseCase
import org.scaleadvisor.backend.project.controller.response.file.GetFileResponse
import org.scaleadvisor.backend.project.domain.File
import org.scaleadvisor.backend.project.domain.VersionNumber
import org.scaleadvisor.backend.project.domain.enum.FileType
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
private class ProjectFileController(
    private val createFileUseCase: CreateFileUseCase,
    private val getFileUseCase: GetFileUseCase,
    private val downloadFileUseCase: DownloadFileUseCase,
) : ProjectFileAPI {
    override fun upload(
        projectId: Long,
        name: String,
        type: FileType,
        file: MultipartFile
    ) {
        val currentUserId = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw UnauthorizedException("만료 되거나 잘못된 인증 입니다.")

        createFileUseCase.create(
            CreateFileUseCase.Command(
                projectId = ProjectId.of(projectId),
                name = name,
                type = type,
                uploaderId = currentUserId,
                file = file
            )
        )
    }

    override fun find(
        projectId: Long,
        versionNumber: String
    ): SuccessResponse<GetFileResponse> {
        val file = getFileUseCase.find(
            GetFileUseCase.Command(
                projectId = ProjectId.of(projectId),
                versionNumber = VersionNumber.of(versionNumber)
            )
        )

        return SuccessResponse.from(GetFileResponse.from(file))
    }

    override fun download(
        projectId: Long,
        versionNumber: String
    ): ResponseEntity<ByteArrayResource> {
        val file: File = getFileUseCase.find(
            GetFileUseCase.Command(
                projectId = ProjectId.of(projectId),
                versionNumber = VersionNumber.of(versionNumber)
            )
        )

        val fileData: ByteArray = downloadFileUseCase.download(file.path)

        val resource = ByteArrayResource(fileData)

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${file.name}\"")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource)
    }
}