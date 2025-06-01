package org.scaleadvisor.backend.project.application.port.usecase.version

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.enum.AdjustmentFactorType
import org.scaleadvisor.backend.project.domain.enum.FunctionType

interface UpdateProjectVersionUseCase {

    data class Command(
        val projectVersion: ProjectVersion,
        val requirementList: List<RequirementDTO>,
        val unitProcessList: List<UnitProcessDTO>,
        val adjustmentFactorList: List<AdjustmentFactorDTO>,
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

    data class AdjustmentFactorDTO(
        var adjustmentFactorId: String,
        val adjustmentFactorLevel: Int,
    )

    fun update(command: Command)
}