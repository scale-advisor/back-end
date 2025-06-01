package org.scaleadvisor.backend.project.application.port.usecase.version

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.enum.FunctionType

interface CreateProjectVersionUseCase {

    data class Command(
        val projectVersion: ProjectVersion,
        val requirementList: List<RequirementDTO>,
        val unitProcessList: List<UnitProcessDTO>,
    )

    data class RequirementDTO(
        var requirementId: String?,
        val requirementNumber: String,
        val requirementName: String,
        val requirementDefinition: String,
        val requirementDetail: String,
        val requirementType: String,
    )

    data class UnitProcessDTO(
        var unitProcessId: String?,
        val unitProcessName: String,
        val functionType: FunctionType,
        val isAmbiguous: Boolean,
    )

    fun create(command: Command): ProjectVersion
}