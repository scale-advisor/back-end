package org.scaleadvisor.backend.project.api.response

import org.scaleadvisor.backend.project.domain.CocomoScaleFactor
import java.time.LocalDateTime

data class UpdateCocomoScaleFactorResponse(
    val id: String,
    val projectId: String,
    val prec: String,
    val flex: String,
    val resl: String,
    val team: String,
    val pmat: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        @JvmStatic
        fun from(scaleFactor: CocomoScaleFactor): UpdateCocomoScaleFactorResponse {
            return UpdateCocomoScaleFactorResponse(
                id = scaleFactor.cocomoScaleFactorId.toString(),
                projectId = scaleFactor.projectId.toString(),
                prec = scaleFactor.prec.name,
                flex = scaleFactor.flex.name,
                resl = scaleFactor.resl.name,
                team = scaleFactor.team.name,
                pmat = scaleFactor.pmat.name,
                createdAt = scaleFactor.createdAt,
                updatedAt = scaleFactor.updatedAt ?: scaleFactor.createdAt
            )
        }
    }
}