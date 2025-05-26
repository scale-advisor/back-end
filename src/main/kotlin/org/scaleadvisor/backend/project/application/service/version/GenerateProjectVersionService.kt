package org.scaleadvisor.backend.project.application.service.version

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.version.CreateProjectVersionPort
import org.scaleadvisor.backend.project.application.port.usecase.version.GenerateProjectVersionUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.GetProjectVersionUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class GenerateProjectVersionService(
    private val getProjectVersionUseCase: GetProjectVersionUseCase,
    private val createProjectVersionPort: CreateProjectVersionPort
) : GenerateProjectVersionUseCase {

    override fun generateNextMajorVersion(projectId: ProjectId): ProjectVersion {
        val projectVersion: ProjectVersion =
            getProjectVersionUseCase.findLatest(projectId)?.nextMajor() ?: ProjectVersion.init(projectId)

        createProjectVersionPort.create(projectVersion)

        return projectVersion
    }

    override fun generateNextMinorVersion(projectId: ProjectId): ProjectVersion {
        val projectVersion: ProjectVersion =
            getProjectVersionUseCase.findLatest(projectId)?.nextMinor()
                ?: throw NotFoundException("Project version not found")

        createProjectVersionPort.create(projectVersion)

        return projectVersion
    }

}