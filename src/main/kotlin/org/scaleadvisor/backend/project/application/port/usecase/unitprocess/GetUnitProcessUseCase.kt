package org.scaleadvisor.backend.project.application.port.usecase.unitprocess

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.scaleadvisor.backend.project.domain.enum.FunctionType
import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.scaleadvisor.backend.project.domain.id.UnitProcessId

interface GetUnitProcessUseCase {
    fun findAll(projectVersion: ProjectVersion): List<UnitProcess>

    fun findAllId(projectVersion: ProjectVersion): List<UnitProcessId>

    data class UnitProcessWithRequirementIdListDTO(
        val unitProcessId: String,
        val unitProcessName: String,
        val functionType: FunctionType,
        val isAmbiguous: Boolean,
        val requirementIdList: List<String>
    ) {
        companion object {
            @JvmStatic
            fun from(unitProcess: UnitProcess, requirementIdList: List<RequirementId>): UnitProcessWithRequirementIdListDTO = UnitProcessWithRequirementIdListDTO(
                unitProcess.id.toString(),
                unitProcess.name,
                unitProcess.functionType,
                unitProcess.isAmbiguous,
                requirementIdList.map { requirementId -> requirementId.toString() }
            )
        }
    }

    fun findAllWithRequirementIdList(projectVersion: ProjectVersion): List<UnitProcessWithRequirementIdListDTO>
}