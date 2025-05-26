package org.scaleadvisor.backend.project.domain

import org.scaleadvisor.backend.project.domain.id.FpWeightsId
import org.scaleadvisor.backend.project.domain.id.ProjectId
import java.time.LocalDateTime

data class FpWeights(
    val fpWeightsId: FpWeightsId?,
    val projectId: ProjectId,

    var ilfWeight: Double,
    var eifWeight: Double,
    var eiWeight: Double,
    var eoWeight: Double,
    var eqWeight: Double,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime?
) {
    val isGovStandard: Boolean
        get() = (ilfWeight == DEFAULT_ILF
                && eifWeight == DEFAULT_EIF
                && eiWeight == DEFAULT_EI
                && eoWeight == DEFAULT_EO
                && eqWeight == DEFAULT_EQ)

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

    companion object {
        /**― 기본값 상수 ―*/
        private const val DEFAULT_ILF = 7.5
        private const val DEFAULT_EIF = 5.4
        private const val DEFAULT_EI  = 4.0
        private const val DEFAULT_EO  = 5.2
        private const val DEFAULT_EQ  = 3.9

        /**
         * DB에 레코드가 없을 때 호출해 사용할 팩토리
         */
        fun getGovStandard(projectId: ProjectId): FpWeights =
            FpWeights(
                fpWeightsId = null,   // ⇢ 새 키 생성 방식에 맞게 교체
                projectId   = projectId,
                ilfWeight   = DEFAULT_ILF,
                eifWeight   = DEFAULT_EIF,
                eiWeight    = DEFAULT_EI,
                eoWeight    = DEFAULT_EO,
                eqWeight    = DEFAULT_EQ,
                createdAt   = LocalDateTime.now(),
                updatedAt   = null
            )
    }
}
