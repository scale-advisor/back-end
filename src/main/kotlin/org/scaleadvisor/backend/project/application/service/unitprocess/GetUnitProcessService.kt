package org.scaleadvisor.backend.project.application.service.unitprocess

import org.scaleadvisor.backend.project.application.port.repository.unitprocess.GetUnitProcessPort
import org.scaleadvisor.backend.project.application.port.usecase.requirement.GetRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.GetUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.scaleadvisor.backend.project.domain.id.UnitProcessId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetUnitProcessService(
    private val getUnitProcessPort: GetUnitProcessPort,
    private val getRequirementUseCase: GetRequirementUseCase,
): GetUnitProcessUseCase {
    override fun findAll(projectVersion: ProjectVersion): List<UnitProcess> {
        return getUnitProcessPort.findAll(projectVersion)
    }

    override fun findAllId(projectVersion: ProjectVersion): List<UnitProcessId> {
        return getUnitProcessPort.findAllId(projectVersion)
    }

    override fun findAllWithRequirementIdList(projectVersion: ProjectVersion): List<GetUnitProcessUseCase.UnitProcessWithRequirementIdListDTO> {
        val unitProcessList = getUnitProcessPort.findAll(projectVersion)
        return unitProcessList.map { unitProcess ->
            val requirementIdList = getRequirementUseCase.findAllId(unitProcess.id)
            GetUnitProcessUseCase.UnitProcessWithRequirementIdListDTO.from(
                unitProcess,
                requirementIdList
            )
        }
    }
}