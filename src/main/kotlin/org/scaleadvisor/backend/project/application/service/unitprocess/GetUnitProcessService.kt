package org.scaleadvisor.backend.project.application.service.unitprocess

import org.scaleadvisor.backend.project.application.port.repository.unitprocess.GetUnitProcessPort
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.GetUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetUnitProcessService(
    private val getUnitProcessPort: GetUnitProcessPort,
): GetUnitProcessUseCase {
    override fun findAll(projectVersion: ProjectVersion): List<UnitProcess> {
        return getUnitProcessPort.findAll(projectVersion)
    }
}