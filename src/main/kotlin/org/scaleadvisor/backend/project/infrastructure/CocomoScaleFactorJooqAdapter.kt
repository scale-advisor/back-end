package org.scaleadvisor.backend.project.infrastructure

import org.jooq.DSLContext
import org.jooq.generated.Tables.COCOMO_SCALE_FACTOR
import org.jooq.generated.tables.records.CocomoScaleFactorRecord
import org.scaleadvisor.backend.project.application.port.repository.cocomoscalefactor.CreateCocomoScaleFactorPort
import org.scaleadvisor.backend.project.application.port.repository.cocomoscalefactor.DeleteCocomoScaleFactorPort
import org.scaleadvisor.backend.project.application.port.repository.cocomoscalefactor.FindCocomoScaleFactorPort
import org.scaleadvisor.backend.project.application.port.repository.cocomoscalefactor.UpdateCocomoScaleFactorPort
import org.scaleadvisor.backend.project.domain.CocomoScaleFactor
import org.scaleadvisor.backend.project.domain.enum.CocomoScaleFactorLevel
import org.scaleadvisor.backend.project.domain.id.CocomoScaleFactorId
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Repository

@Repository
private class CocomoScaleFactorAdapter(
    private val dsl: DSLContext
): CreateCocomoScaleFactorPort,
    FindCocomoScaleFactorPort,
    UpdateCocomoScaleFactorPort,
    DeleteCocomoScaleFactorPort {
    private fun CocomoScaleFactorRecord.toDomain(): CocomoScaleFactor =
        CocomoScaleFactor(
            cocomoScaleFactorId = CocomoScaleFactorId.of(this.cocomoScaleFactorId),
            projectId = ProjectId.of(this.projectId),
            prec = CocomoScaleFactorLevel.valueOf(this.prec),
            flex = CocomoScaleFactorLevel.valueOf(this.flex),
            resl = CocomoScaleFactorLevel.valueOf(this.resl),
            team = CocomoScaleFactorLevel.valueOf(this.team),
            pmat = CocomoScaleFactorLevel.valueOf(this.pmat),
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )

    override fun create(cocomoScaleFactor: CocomoScaleFactor) {
        dsl.insertInto(COCOMO_SCALE_FACTOR)
            .set(COCOMO_SCALE_FACTOR.COCOMO_SCALE_FACTOR_ID, cocomoScaleFactor.cocomoScaleFactorId.toLong())
            .set(COCOMO_SCALE_FACTOR.PROJECT_ID, cocomoScaleFactor.projectId.toLong())
            .set(COCOMO_SCALE_FACTOR.PREC, cocomoScaleFactor.prec.name)
            .set(COCOMO_SCALE_FACTOR.FLEX, cocomoScaleFactor.flex.name)
            .set(COCOMO_SCALE_FACTOR.RESL, cocomoScaleFactor.resl.name)
            .set(COCOMO_SCALE_FACTOR.TEAM, cocomoScaleFactor.team.name)
            .set(COCOMO_SCALE_FACTOR.PMAT, cocomoScaleFactor.pmat.name)
            .set(COCOMO_SCALE_FACTOR.CREATED_AT, cocomoScaleFactor.createdAt)
            .set(COCOMO_SCALE_FACTOR.UPDATED_AT, cocomoScaleFactor.updatedAt)
            .execute()
    }

    override fun findByProjectId(projectId: ProjectId): CocomoScaleFactor? {
        return dsl.selectFrom(COCOMO_SCALE_FACTOR)
            .where(COCOMO_SCALE_FACTOR.PROJECT_ID.eq(projectId.toLong()))
            .fetchOne { it.into(COCOMO_SCALE_FACTOR).toDomain() }
    }

    override fun update(cocomoScaleFactor: CocomoScaleFactor) {
        dsl.update(COCOMO_SCALE_FACTOR)
            .set(COCOMO_SCALE_FACTOR.PREC, cocomoScaleFactor.prec.name)
            .set(COCOMO_SCALE_FACTOR.FLEX, cocomoScaleFactor.flex.name)
            .set(COCOMO_SCALE_FACTOR.RESL, cocomoScaleFactor.resl.name)
            .set(COCOMO_SCALE_FACTOR.TEAM, cocomoScaleFactor.team.name)
            .set(COCOMO_SCALE_FACTOR.PMAT, cocomoScaleFactor.pmat.name)
            .set(COCOMO_SCALE_FACTOR.UPDATED_AT, cocomoScaleFactor.updatedAt)
            .where(COCOMO_SCALE_FACTOR.COCOMO_SCALE_FACTOR_ID.eq(cocomoScaleFactor.cocomoScaleFactorId.toLong()))
            .execute()
    }

    override fun delete(projectId: ProjectId) {
        dsl.deleteFrom(COCOMO_SCALE_FACTOR)
            .where(COCOMO_SCALE_FACTOR.PROJECT_ID.eq(projectId.toLong()))
            .execute()
    }
}