package org.scaleadvisor.backend.project.application.service.cocomomultiplier

import org.scaleadvisor.backend.project.application.port.repository.cocomomultiplier.FindCocomoMultiplierPort
import org.scaleadvisor.backend.project.application.port.usecase.cocomomultiplier.FindCocomoMultiplierUseCase
import org.scaleadvisor.backend.project.domain.CocomoMultiplier
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class FindCocomoMultiplierService(
    private val findCocomoMultiplierPort: FindCocomoMultiplierPort
): FindCocomoMultiplierUseCase {

    override fun find(projectId: ProjectId): CocomoMultiplier? {
        return findCocomoMultiplierPort.findByProjectId(projectId)
    }

}