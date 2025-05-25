package org.scaleadvisor.backend.project.application.service.version

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.version.CreateVersionPort
import org.scaleadvisor.backend.project.application.port.usecase.version.GenerateVersionUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.GetVersionUseCase
import org.scaleadvisor.backend.project.domain.Version
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.vo.VersionNumber
import org.springframework.stereotype.Service

@Service
private class GenerateVersionService(
    private val getVersionUseCase: GetVersionUseCase,
    private val createVersionPort: CreateVersionPort
) : GenerateVersionUseCase {

    override fun generateNextMajorVersion(projectId: ProjectId): VersionNumber {
        val versionNumber: VersionNumber =
            getVersionUseCase.findLatest(projectId)?.nextMajor() ?: VersionNumber.INITIAL_NUMBER

        val version = Version(projectId, versionNumber)
        createVersionPort.create(version)

        return versionNumber
    }

    override fun generateNextMinorVersion(projectId: ProjectId): VersionNumber {
        val versionNumber: VersionNumber =
            getVersionUseCase.findLatest(projectId)?.nextMinor()
                ?: throw NotFoundException("Project version not found")

        val version = Version(projectId, versionNumber)
        createVersionPort.create(version)

        return versionNumber
    }

}