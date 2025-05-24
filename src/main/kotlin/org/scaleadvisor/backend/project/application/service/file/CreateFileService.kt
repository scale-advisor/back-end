package org.scaleadvisor.backend.project.application.service.file

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.global.util.FileUtil
import org.scaleadvisor.backend.project.application.port.repository.file.CreateFilePort
import org.scaleadvisor.backend.project.application.port.usecase.file.CreateFileUseCase
import org.scaleadvisor.backend.project.application.port.usecase.file.UploadFileUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.GetProjectUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.GetVersionUseCase
import org.scaleadvisor.backend.project.domain.File
import org.scaleadvisor.backend.project.domain.Project
import org.scaleadvisor.backend.project.domain.id.FileId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
@Transactional
private class CreateFileService(
    private val getProjectUseCase: GetProjectUseCase,
    private val getVersionUseCase: GetVersionUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val createFilePort: CreateFilePort
) : CreateFileUseCase {

    override fun create(command: CreateFileUseCase.Command) {
        val project: Project = getProjectUseCase.find(command.projectId)
            ?: throw NotFoundException("Project not found")

        val versionNumber: String = getVersionUseCase.findLatest(project.id)
        val path: String = project.id.toString() + "/" + versionNumber + "/" + command.file.originalFilename

        val file = File(
            id = FileId.newId(),
            projectId = project.id,
            versionNumber = versionNumber,
            name = command.name,
            type = command.type,
            uploaderId = command.uploaderId,
            path = path,
            extension = FileUtil.getExtension(command.file),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        createFilePort.create(file)

        uploadFileUseCase.upload(
            UploadFileUseCase.Command(
                projectId = project.id,
                file = command.file,
                path = path
            )
        )
    }



}