package org.scaleadvisor.backend.project.application.service.file

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.file.GetFilePort
import org.scaleadvisor.backend.project.application.port.usecase.file.GetFileUseCase
import org.scaleadvisor.backend.project.domain.File
import org.springframework.stereotype.Service

@Service
private class GetFileService(
    private val getFilePort: GetFilePort
) : GetFileUseCase {

    override fun find(command: GetFileUseCase.Command): File {
        return getFilePort.find(
            projectId = command.projectId,
            versionNumber = command.versionNumber
        ) ?: throw NotFoundException("File not found")
    }

}