package org.scaleadvisor.backend.project.application.service.adjustmentfactor

import org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor.DeleteAdjustmentFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor.DeleteAdjustmentFactorUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteAdjustmentFactorService(
    private val deleteAdjustmentFactorPort: DeleteAdjustmentFactorPort,
) : DeleteAdjustmentFactorUseCase {

    override fun deleteAll(projectId: ProjectId) {
        deleteAdjustmentFactorPort.deleteAll(projectId)
    }

    override fun deleteAll(
        projectId: ProjectId,
        versionMajorNumber: Int
    ) {
        deleteAdjustmentFactorPort.deleteAll(
            projectId,
            versionMajorNumber
        )
    }

    override fun deleteAll(projectVersion: ProjectVersion) {
        deleteAdjustmentFactorPort.deleteAll(projectVersion)
    }

}