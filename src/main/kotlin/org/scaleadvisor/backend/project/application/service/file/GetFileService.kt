package org.scaleadvisor.backend.project.application.service.file

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.file.GetFilePort
import org.scaleadvisor.backend.project.application.port.usecase.file.GetFileUseCase
import org.scaleadvisor.backend.project.domain.File
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetFileService(
    private val getFilePort: GetFilePort
) : GetFileUseCase {

    override fun find(projectVersion: ProjectVersion): File {
        return getFilePort.find(
            projectVersion = projectVersion
        ) ?: throw NotFoundException("File not found")
    }

}