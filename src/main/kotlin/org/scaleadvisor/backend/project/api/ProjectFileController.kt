package org.scaleadvisor.backend.project.api

import org.scaleadvisor.backend.api.ProjectFileAPI
import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.scaleadvisor.backend.project.application.port.usecase.file.CreateFileUseCase
import org.scaleadvisor.backend.project.domain.enum.FileType
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
private class ProjectFileController(
    private val createFileUseCase: CreateFileUseCase
) : ProjectFileAPI {
    override fun upload(
        projectId: Long,
        name: String,
        type: FileType,
        file: MultipartFile
    ) {
        val currentUserId = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw ForbiddenException("현재 인증이 되지 않은 접근 입니다.")

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
}