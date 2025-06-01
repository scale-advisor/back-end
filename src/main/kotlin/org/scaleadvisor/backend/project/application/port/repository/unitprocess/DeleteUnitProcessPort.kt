package org.scaleadvisor.backend.project.application.port.repository.unitprocess

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.id.UnitProcessId

interface DeleteUnitProcessPort {
    fun deleteAll(projectId: ProjectId)
    fun deleteAll(projectId: ProjectId, versionMajorNumber: Int)
    fun deleteAll(projectVersion: ProjectVersion)
    fun deleteAll(unitProcessIdList: List<UnitProcessId>)
}