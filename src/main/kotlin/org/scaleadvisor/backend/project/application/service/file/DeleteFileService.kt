package org.scaleadvisor.backend.project.application.service.file

import org.scaleadvisor.backend.project.application.port.repository.file.DeleteFilePort
import org.scaleadvisor.backend.project.application.port.usecase.file.DeleteFileUseCase
import org.scaleadvisor.backend.project.application.port.usecase.file.RemoveFileUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteFileService(
    private val removeFileUseCase: RemoveFileUseCase,
    private val deleteFilePort: DeleteFilePort,
) : DeleteFileUseCase {
    override fun deleteAll(projectId: ProjectId) {
        deleteFilePort.deleteAll(projectId)
        removeFileUseCase.removeAll(projectId)
    }

}