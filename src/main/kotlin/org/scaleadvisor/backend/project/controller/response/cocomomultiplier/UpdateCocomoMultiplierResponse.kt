package org.scaleadvisor.backend.project.controller.response.cocomomultiplier

import org.scaleadvisor.backend.project.domain.CocomoMultiplier
import java.time.LocalDateTime

data class UpdateCocomoMultiplierResponse(
    val id: String,
    val projectId: String,
    val rcpx: String,
    val ruse: String,
    val pdif: String,
    val pers: String,
    val sced: String,
    val fcil: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        @JvmStatic
        fun from(multiplier: CocomoMultiplier): UpdateCocomoMultiplierResponse {
            return UpdateCocomoMultiplierResponse(
                id = multiplier.cocomoMultiplierId.toString(),
                projectId = multiplier.projectId.toString(),
                rcpx = multiplier.rcpx.name,
                ruse = multiplier.ruse.name,
                pdif = multiplier.pdif.name,
                pers = multiplier.pers.name,
                sced = multiplier.sced.name,
                fcil = multiplier.fcil.name,
                createdAt = multiplier.createdAt,
                updatedAt = multiplier.updatedAt ?: multiplier.createdAt
            )
        }
    }
}