package org.scaleadvisor.backend.project.domain

import org.scaleadvisor.backend.project.domain.id.FpWeightsId
import org.scaleadvisor.backend.project.domain.id.ProjectId
import java.time.LocalDateTime

data class FpWeights(
    val fpWeightsId: FpWeightsId,
    val projectId: ProjectId,

    var ilfWeight: Double,
    var eifWeight: Double,
    var eiWeight: Double,
    var eoWeight: Double,
    var eqWeight: Double,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime?
) {
    fun update(
        ilfWeight: Double,
        eifWeight: Double,
        eiWeight: Double,
        eoWeight: Double,
        eqWeight: Double) {
        this.ilfWeight = ilfWeight
        this.eifWeight = eifWeight
        this.eiWeight = eiWeight
        this.eoWeight = eoWeight
        this.eqWeight = eqWeight
        this.updatedAt = LocalDateTime.now()
    }
}
