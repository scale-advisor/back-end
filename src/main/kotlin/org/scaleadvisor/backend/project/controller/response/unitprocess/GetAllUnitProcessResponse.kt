package org.scaleadvisor.backend.project.controller.response.unitprocess

import org.scaleadvisor.backend.project.domain.UnitProcess
import org.scaleadvisor.backend.project.domain.enum.FunctionType

data class GetAllUnitProcessResponse (
    val unitProcessList: List<UnitProcessDTO>
) {
    data class UnitProcessDTO (
        val unitProcessId: String,
        val unitProcessName: String,
        val functionType: FunctionType,
        val isAmbiguous: Boolean,
    ) {
        companion object {
            @JvmStatic
            fun from(unitProcess: UnitProcess): UnitProcessDTO = UnitProcessDTO(
                unitProcess.id.toString(),
                unitProcess.name,
                unitProcess.functionType,
                unitProcess.isAmbiguous,
            )
        }
    }

    companion object {
        @JvmStatic
        fun from(unitProcessList: List<UnitProcess>): GetAllUnitProcessResponse = GetAllUnitProcessResponse(
            unitProcessList.map {
                UnitProcessDTO.from(it)
            }
        )
    }
}