package org.scaleadvisor.backend.project.controller.request.version

import org.scaleadvisor.backend.project.application.port.usecase.version.UpdateProjectVersionUseCase

data class UpdateProjectVersionRequest(
    var requirementList: List<UpdateProjectVersionUseCase.RequirementDTO>,
    var unitProcessList: List<UpdateProjectVersionUseCase.UnitProcessDTO>,
    var adjustmentFactorList: List<UpdateProjectVersionUseCase.AdjustmentFactorDTO>,
)
