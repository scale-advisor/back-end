package org.scaleadvisor.backend.project.application.port.repository.unitprocess

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.UnitProcess

interface GetUnitProcessPort {

    fun findAll(projectVersion: ProjectVersion): List<UnitProcess>

}