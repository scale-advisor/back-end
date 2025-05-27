package org.scaleadvisor.backend.project.application.service.file

import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.global.util.FileUtil
import org.scaleadvisor.backend.project.application.port.repository.file.CreateFilePort
import org.scaleadvisor.backend.project.application.port.usecase.file.CreateFileUseCase
import org.scaleadvisor.backend.project.application.port.usecase.file.UploadFileUseCase
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsEditorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.GetProjectUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.GenerateProjectVersionUseCase
import org.scaleadvisor.backend.project.domain.File
import org.scaleadvisor.backend.project.domain.Project
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.FileId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
@Transactional
private class CreateFileService(
    private val checkIsEditorUseCase: CheckIsEditorUseCase,
    private val getProjectUseCase: GetProjectUseCase,
    private val generateProjectVersionUseCase: GenerateProjectVersionUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val createFilePort: CreateFilePort
) : CreateFileUseCase {

    override fun create(command: CreateFileUseCase.Command) {
        if (!checkIsEditorUseCase.checkIsEditor(command.projectId.toLong())) {
            throw ForbiddenException("프로젝트 요소를 수정할 권한이 없습니다.")
        }

        val project: Project = getProjectUseCase.find(command.projectId)
            ?: throw NotFoundException("Project not found")


        val projectVersion: ProjectVersion =
            generateProjectVersionUseCase.generateNextMajorVersion(project.id)

        val path: String = project.id.toString() + "/" + projectVersion.versionNumber + "/" + command.file.originalFilename

        val file = File(
            id = FileId.newId(),
            projectId = project.id,
            projectVersion = projectVersion,
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