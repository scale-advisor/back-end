package org.scaleadvisor.backend.project.application.service.file

import org.scaleadvisor.backend.project.application.port.repository.file.UploadFilePort
import org.scaleadvisor.backend.project.application.port.usecase.file.UploadFileUseCase
import org.springframework.stereotype.Service


@Service
private class UploadFileService(
    private val uploadFilePort: UploadFilePort
) : UploadFileUseCase {

    override fun upload(command: UploadFileUseCase.Command) {
        uploadFilePort.upload(
            projectId = command.projectId,
            file = command.file,
            path = command.path
        )
    }

}