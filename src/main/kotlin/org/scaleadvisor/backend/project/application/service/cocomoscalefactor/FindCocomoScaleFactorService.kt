package org.scaleadvisor.backend.project.application.service.cocomoscalefactor

import org.scaleadvisor.backend.project.application.port.repository.cocomoscalefactor.FindCocomoScaleFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.cocomoscalefactor.FindCocomoScaleFactorUseCase
import org.scaleadvisor.backend.project.domain.CocomoScaleFactor
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service

@Service
private class FindCocomoScaleFactorService(
    private val findCocomoScaleFactorPort: FindCocomoScaleFactorPort
): FindCocomoScaleFactorUseCase {
    override fun find(projectId: ProjectId): CocomoScaleFactor? {
        return findCocomoScaleFactorPort.findByProjectId(projectId)
    }
}