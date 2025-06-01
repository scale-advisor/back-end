package org.scaleadvisor.backend.project.controller.request.version

import org.scaleadvisor.backend.project.application.port.usecase.version.CreateProjectVersionUseCase

data class CreateProjectVersionRequest(
    var requirementList: List<CreateProjectVersionUseCase.RequirementDTO>,
    var unitProcessList: List<CreateProjectVersionUseCase.UnitProcessDTO>,
)
