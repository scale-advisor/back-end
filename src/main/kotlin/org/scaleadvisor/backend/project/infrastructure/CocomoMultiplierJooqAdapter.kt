package org.scaleadvisor.backend.project.infrastructure

import org.jooq.DSLContext
import org.jooq.generated.Tables.COCOMO_MULTIPLIER
import org.jooq.generated.tables.records.CocomoMultiplierRecord
import org.scaleadvisor.backend.project.application.port.repository.cocomomultiplier.CreateCocomoMultiplierPort
import org.scaleadvisor.backend.project.domain.CocomoMultiplier
import org.scaleadvisor.backend.project.domain.enum.CocomoLevel
import org.scaleadvisor.backend.project.domain.id.CocomoMultiplierId
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Repository

@Repository
private class CocomoMultiplierJooqAdapter(
    private val dsl: DSLContext
): CreateCocomoMultiplierPort {
    private fun CocomoMultiplierRecord.toDomain(): CocomoMultiplier =
        CocomoMultiplier(
            cocomoMultiplierId = CocomoMultiplierId.of(this.cocomoMultiplierId),
            projectId = ProjectId.of(this.projectId),
            rcpx = CocomoLevel.valueOf(this.rcpx),
            ruse = CocomoLevel.valueOf(this.ruse),
            pdif = CocomoLevel.valueOf(this.pdif),
            pers = CocomoLevel.valueOf(this.pers),
            sced = CocomoLevel.valueOf(this.sced),
            fcil = CocomoLevel.valueOf(this.fcil),
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )

    override fun create(cocomoMultiplier: CocomoMultiplier) {
        dsl.insertInto(COCOMO_MULTIPLIER)
            .set(COCOMO_MULTIPLIER.COCOMO_MULTIPLIER_ID, cocomoMultiplier.cocomoMultiplierId.toLong())
            .set(COCOMO_MULTIPLIER.PROJECT_ID, cocomoMultiplier.projectId.toLong())
            .set(COCOMO_MULTIPLIER.RCPX, cocomoMultiplier.rcpx.name)
            .set(COCOMO_MULTIPLIER.RUSE, cocomoMultiplier.ruse.name)
            .set(COCOMO_MULTIPLIER.PDIF, cocomoMultiplier.pdif.name)
            .set(COCOMO_MULTIPLIER.PERS, cocomoMultiplier.pers.name)
            .set(COCOMO_MULTIPLIER.SCED, cocomoMultiplier.sced.name)
            .set(COCOMO_MULTIPLIER.FCIL, cocomoMultiplier.fcil.name)
            .set(COCOMO_MULTIPLIER.CREATED_AT, cocomoMultiplier.createdAt)
            .set(COCOMO_MULTIPLIER.UPDATED_AT, cocomoMultiplier.updatedAt)
            .execute()
    }
}