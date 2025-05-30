package org.scaleadvisor.backend.project.infrastructure.database

import org.jooq.DSLContext
import org.jooq.generated.Tables.FP_WEIGHTS
import org.jooq.generated.tables.records.FpWeightsRecord
import org.scaleadvisor.backend.project.application.port.repository.fpweights.CreateFpWeightsPort
import org.scaleadvisor.backend.project.application.port.repository.fpweights.DeleteFpWeightsPort
import org.scaleadvisor.backend.project.application.port.repository.fpweights.FindFpWeightsPort
import org.scaleadvisor.backend.project.application.port.repository.fpweights.UpdateFpWeightsPort
import org.scaleadvisor.backend.project.domain.FpWeights
import org.scaleadvisor.backend.project.domain.id.FpWeightsId
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
private class FpWeightsJooqAdapter(
    private val dsl: DSLContext
): CreateFpWeightsPort, FindFpWeightsPort, UpdateFpWeightsPort, DeleteFpWeightsPort {
    private fun FpWeightsRecord.toDomain(): FpWeights =
        FpWeights(
            fpWeightsId = FpWeightsId.of(this.fpWeightsId),
            projectId   = ProjectId.from(this.projectId),
            ilfWeight   = this.ilfWeight.toDouble(),
            eifWeight   = this.eifWeight.toDouble(),
            eiWeight    = this.eiWeight.toDouble(),
            eoWeight    = this.eoWeight.toDouble(),
            eqWeight    = this.eqWeight.toDouble(),
            createdAt   = this.createdAt,
            updatedAt   = this.updatedAt
        )

    override fun create(fpWeights: FpWeights) {
        dsl.insertInto(FP_WEIGHTS)
            .set(FP_WEIGHTS.FP_WEIGHTS_ID, fpWeights.fpWeightsId?.toLong())
            .set(FP_WEIGHTS.PROJECT_ID, fpWeights.projectId.toLong())
            .set(FP_WEIGHTS.ILF_WEIGHT, BigDecimal.valueOf(fpWeights.ilfWeight))
            .set(FP_WEIGHTS.EIF_WEIGHT, BigDecimal.valueOf(fpWeights.eifWeight))
            .set(FP_WEIGHTS.EI_WEIGHT,  BigDecimal.valueOf(fpWeights.eiWeight))
            .set(FP_WEIGHTS.EO_WEIGHT,  BigDecimal.valueOf(fpWeights.eoWeight))
            .set(FP_WEIGHTS.EQ_WEIGHT,  BigDecimal.valueOf(fpWeights.eqWeight))
            .set(FP_WEIGHTS.CREATED_AT, fpWeights.createdAt)
            .set(FP_WEIGHTS.UPDATED_AT, fpWeights.updatedAt!!)
            .execute()
    }

    override fun find(projectId: ProjectId): FpWeights? {
        return dsl.selectFrom(FP_WEIGHTS)
            .where(FP_WEIGHTS.PROJECT_ID.eq(projectId.toLong()))
            .fetchOne { it.into(FP_WEIGHTS).toDomain() }
    }

    override fun update(fpWeights: FpWeights) {
        dsl.update(FP_WEIGHTS)
            .set(FP_WEIGHTS.ILF_WEIGHT, BigDecimal.valueOf(fpWeights.ilfWeight))
            .set(FP_WEIGHTS.EIF_WEIGHT, BigDecimal.valueOf(fpWeights.eifWeight))
            .set(FP_WEIGHTS.EI_WEIGHT,  BigDecimal.valueOf(fpWeights.eiWeight))
            .set(FP_WEIGHTS.EO_WEIGHT,  BigDecimal.valueOf(fpWeights.eoWeight))
            .set(FP_WEIGHTS.EQ_WEIGHT,  BigDecimal.valueOf(fpWeights.eqWeight))
            .set(FP_WEIGHTS.UPDATED_AT, fpWeights.updatedAt!!)
            .where(FP_WEIGHTS.FP_WEIGHTS_ID.eq(fpWeights.fpWeightsId?.toLong()))
            .execute()
    }

    override fun delete(projectId: ProjectId) {
        dsl.deleteFrom(FP_WEIGHTS)
            .where(FP_WEIGHTS.PROJECT_ID.eq(projectId.toLong()))
            .execute()
    }
}