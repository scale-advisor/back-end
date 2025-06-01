package org.scaleadvisor.backend.project.application.port.repository.unitprocess

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.scaleadvisor.backend.project.domain.id.UnitProcessId

interface GetUnitProcessPort {

    fun findAll(projectVersion: ProjectVersion): List<UnitProcess>
    fun findAllId(projectVersion: ProjectVersion): List<UnitProcessId>

}