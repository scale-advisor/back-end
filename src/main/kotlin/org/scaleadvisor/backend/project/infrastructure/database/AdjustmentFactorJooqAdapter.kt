package org.scaleadvisor.backend.project.infrastructure.database

import ProjectVersionId
import org.jooq.DSLContext
import org.jooq.generated.Tables.ADJUSTMENT_FACTOR
import org.jooq.generated.tables.records.AdjustmentFactorRecord
import org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor.CreateAdjustmentFactorPort
import org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor.DeleteAdjustmentFactorPort
import org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor.GetAdjustmentFactorPort
import org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor.UpdateAdjustmentFactorPort
import org.scaleadvisor.backend.project.application.service.adjustmentfactor.dto.UpdateAdjustmentFactorDTO
import org.scaleadvisor.backend.project.domain.AdjustmentFactor
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.enum.AdjustmentFactorType
import org.scaleadvisor.backend.project.domain.id.AdjustmentFactorId
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Repository

@Repository
private class AdjustmentFactorJooqAdapter(
    private val dsl: DSLContext
): CreateAdjustmentFactorPort, GetAdjustmentFactorPort, UpdateAdjustmentFactorPort, DeleteAdjustmentFactorPort {

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

    override fun createAll(adjustmentFactorList: List<AdjustmentFactor>) {
        dsl.batchInsert(adjustmentFactorList.map {
            dsl.newRecord(ADJUSTMENT_FACTOR).apply{
                adjustmentFactorId = it.id.toLong()
                projectId = it.projectVersionId.projectId.toLong()
                versionMajorNumber = it.projectVersionId.major
                versionMinorNumber = it.projectVersionId.minor
                adjustmentFactorType = it.type.name
                adjustmentFactorLevel = it.level
            }
        }).execute()
    }

    override fun updateAll(command: List<UpdateAdjustmentFactorDTO>) {
        dsl.batchUpdate(command.map {
            dsl.newRecord(ADJUSTMENT_FACTOR).apply {
                adjustmentFactorId = it.id.toLong()
                adjustmentFactorLevel = it.level
            }
        }).execute()
    }

    override fun findAll(projectVersionId: ProjectVersionId): List<AdjustmentFactor> {
        return dsl.selectFrom(ADJUSTMENT_FACTOR)
            .where(ADJUSTMENT_FACTOR.PROJECT_ID.eq(projectVersionId.projectId.toLong()))
            .and(ADJUSTMENT_FACTOR.VERSION_MAJOR_NUMBER.eq(projectVersionId.major))
            .and(ADJUSTMENT_FACTOR.VERSION_MINOR_NUMBER.eq(projectVersionId.minor))
            .fetch { record -> record.into(ADJUSTMENT_FACTOR).toDomain() }
    }

    override fun deleteAll(projectId: ProjectId) {
        dsl.deleteFrom(ADJUSTMENT_FACTOR)
            .where(ADJUSTMENT_FACTOR.PROJECT_ID.eq(projectId.toLong()))
            .execute()
    }

    override fun deleteAll(
        projectId: ProjectId,
        versionMajorNumber: Int
    ) {
        dsl.deleteFrom(ADJUSTMENT_FACTOR)
            .where(ADJUSTMENT_FACTOR.PROJECT_ID.eq(projectId.toLong()))
            .and(ADJUSTMENT_FACTOR.VERSION_MAJOR_NUMBER.eq(versionMajorNumber))
            .execute()
    }

    override fun deleteAll(projectVersion: ProjectVersion) {
        dsl.deleteFrom(ADJUSTMENT_FACTOR)
            .where(ADJUSTMENT_FACTOR.PROJECT_ID.eq(projectVersion.projectId.toLong()))
            .and(ADJUSTMENT_FACTOR.VERSION_MAJOR_NUMBER.eq(projectVersion.major))
            .and(ADJUSTMENT_FACTOR.VERSION_MINOR_NUMBER.eq(projectVersion.minor))
            .execute()
    }

}