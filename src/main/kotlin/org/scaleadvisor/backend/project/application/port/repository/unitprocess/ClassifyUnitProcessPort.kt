package org.scaleadvisor.backend.project.application.port.repository.unitprocess

import org.scaleadvisor.backend.project.domain.UnitProcess

interface ClassifyUnitProcessPort {
    operator fun invoke(unitProcessList: List<UnitProcess>): List<UnitProcess>
}