package org.scaleadvisor.backend.project.controller.request.unitprocess

data class UpdateAllUnitProcessRequest (
    val requirementList: List<UnitProcessDTO>
) {
    data class UnitProcessDTO (
        val id: String,
    )
}