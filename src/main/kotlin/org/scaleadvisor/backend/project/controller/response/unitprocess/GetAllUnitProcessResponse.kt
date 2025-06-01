package org.scaleadvisor.backend.project.controller.response.unitprocess

import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.GetUnitProcessUseCase

data class GetAllUnitProcessResponse (
    val unitProcessList: List<GetUnitProcessUseCase.UnitProcessWithRequirementIdListDTO>
)