package org.scaleadvisor.backend.project.application.service.projectfactor

import org.scaleadvisor.backend.project.application.port.repository.projectfactor.GetProjectFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.projectfactor.GetProjectFactorUseCase
import org.scaleadvisor.backend.project.domain.ProjectFactor
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service

@Service
private class GetProjectFactorService(
    private val getProjectFactorPort: GetProjectFactorPort
) : GetProjectFactorUseCase {

    override fun find(projectId: ProjectId): ProjectFactor? {
        return getProjectFactorPort.find(projectId)
    }

}