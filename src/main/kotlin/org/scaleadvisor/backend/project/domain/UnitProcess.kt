package org.scaleadvisor.backend.project.domain

import ProjectVersionId
import org.scaleadvisor.backend.project.domain.enum.FunctionType
import org.scaleadvisor.backend.project.domain.id.UnitProcessId

data class UnitProcess(
    val id: UnitProcessId,
    val projectVersionId: ProjectVersionId,
    val name: String,
    val functionType: FunctionType,
    val isAmbiguous: Boolean,
)