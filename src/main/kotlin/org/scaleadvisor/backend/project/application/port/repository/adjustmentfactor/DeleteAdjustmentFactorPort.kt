package org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId

interface DeleteAdjustmentFactorPort {

    fun deleteAll(projectId: ProjectId)

    fun deleteAll(projectId: ProjectId, versionMajorNumber: Int)

    fun deleteAll(projectVersion: ProjectVersion)

}