package org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface DeleteAdjustmentFactorUseCase {

    fun deleteAll(projectId: ProjectId)
    fun deleteAll(projectId: ProjectId, versionMajorNumber: Int)
    fun deleteAll(projectVersion: ProjectVersion)

}