package org.scaleadvisor.backend.project.infrastructure.database

import ProjectVersionId
import org.jooq.DSLContext
import org.jooq.generated.Tables.ADJUSTMENT_FACTOR
import org.jooq.generated.tables.records.AdjustmentFactorRecord
import org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor.GetAdjustmentFactorPort
import org.scaleadvisor.backend.project.domain.AdjustmentFactor
import org.scaleadvisor.backend.project.domain.enum.AdjustmentFactorType
import org.scaleadvisor.backend.project.domain.id.AdjustmentFactorId
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Repository

@Repository
private class AdjustmentFactorJooqAdapter(
    private val dsl: DSLContext
): GetAdjustmentFactorPort {

    private fun AdjustmentFactorRecord.toDomain() = AdjustmentFactor(
        id = AdjustmentFactorId.from(this.adjustmentFactorId),
        projectVersionId = ProjectVersionId.of(
            projectId = ProjectId.from(this.projectId),
            major = this.versionMajorNumber,
            minor = this.versionMinorNumber,
        ),
        type = AdjustmentFactorType.valueOf(this.adjustmentFactorType),
        level = this.adjustmentFactorLevel,
    )

    override fun findAll(projectVersionId: ProjectVersionId): List<AdjustmentFactor> {
        return dsl.selectFrom(ADJUSTMENT_FACTOR)
            .where(ADJUSTMENT_FACTOR.PROJECT_ID.eq(projectVersionId.projectId.toLong()))
            .and(ADJUSTMENT_FACTOR.VERSION_MAJOR_NUMBER.eq(projectVersionId.major))
            .and(ADJUSTMENT_FACTOR.VERSION_MINOR_NUMBER.eq(projectVersionId.minor))
            .fetch { record -> record.into(ADJUSTMENT_FACTOR).toDomain() }
    }

}