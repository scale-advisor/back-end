package org.scaleadvisor.backend.project.application.service.version

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.version.GetProjectVersionPort
import org.scaleadvisor.backend.project.application.port.usecase.version.GetProjectVersionUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetProjectVersionService(
    private val getProjectVersionPort: GetProjectVersionPort
) : GetProjectVersionUseCase {
    override fun findLatest(projectId: ProjectId): ProjectVersion? {
        return getProjectVersionPort.findOrderByVersionNumberDesc(projectId)
    }

    override fun findLatestMinor(projectVersion: ProjectVersion): Int {
        return getProjectVersionPort.findOrderByVersionNumberDesc(
            projectVersion.projectId,
            projectVersion.major
        )?.minor ?: throw NotFoundException("해당 major 버전이 존재하지 않습니다.")
    }

    override fun findAll(projectId: ProjectId): List<ProjectVersion> {
        return getProjectVersionPort.findAll(projectId)
    }
}