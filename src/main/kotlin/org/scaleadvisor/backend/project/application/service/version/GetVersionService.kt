package org.scaleadvisor.backend.project.application.service.version

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.version.GetVersionPort
import org.scaleadvisor.backend.project.application.port.usecase.version.GetVersionUseCase
import org.scaleadvisor.backend.project.domain.Version
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetVersionService(
    private val getVersionPort: GetVersionPort
) : GetVersionUseCase {
    override fun findLatest(projectId: ProjectId): String {
        val version: Version = getVersionPort.findOrderByVersionNumberDesc(projectId)
            ?: throw NotFoundException("Version not found")
        return version.versionNumber
    }

    override fun findAll(projectId: ProjectId): List<String> {
        return getVersionPort.findAll(projectId).map { it.versionNumber }
    }
}