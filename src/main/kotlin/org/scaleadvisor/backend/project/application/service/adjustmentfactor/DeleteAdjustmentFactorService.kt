package org.scaleadvisor.backend.project.application.service.adjustmentfactor

import org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor.DeleteAdjustmentFactorPort
import org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor.UpdateAdjustmentFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor.DeleteAdjustmentFactorUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteAdjustmentFactorService(
    private val deleteAdjustmentFactorPort: DeleteAdjustmentFactorPort,
): DeleteAdjustmentFactorUseCase {

    override fun deleteAll(projectId: ProjectId) {
        deleteAdjustmentFactorPort.deleteAll(projectId)
    }

    override fun deleteAll(projectVersion: ProjectVersion) {
        if (projectVersion.minor == 0) {
            deleteAdjustmentFactorPort.deleteAll(
                projectId = projectVersion.projectId,
                versionMajorNumber = projectVersion.major
            )
        } else {
            deleteAdjustmentFactorPort.deleteAll(projectVersion)
        }
    }


}