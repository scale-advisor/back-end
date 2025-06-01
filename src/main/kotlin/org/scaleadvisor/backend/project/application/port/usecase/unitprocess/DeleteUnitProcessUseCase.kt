package org.scaleadvisor.backend.project.application.port.usecase.unitprocess

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.id.UnitProcessId

interface DeleteUnitProcessUseCase {
    fun deleteAll(projectId: ProjectId)
    fun deleteAll(projectVersion: ProjectVersion)
    fun deleteAll(unitProcessIdList: List<UnitProcessId>)
}