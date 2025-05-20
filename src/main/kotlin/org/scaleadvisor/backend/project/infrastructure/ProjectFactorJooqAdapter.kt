package org.scaleadvisor.backend.project.infrastructure

import org.jooq.DSLContext
import org.jooq.generated.Tables.PROJECT_FACTOR
import org.jooq.generated.tables.records.ProjectFactorRecord
import org.scaleadvisor.backend.global.util.IdUtil
import org.scaleadvisor.backend.project.application.port.repository.projectfactor.CreateProjectFactorPort
import org.scaleadvisor.backend.project.application.port.repository.projectfactor.GetProjectFactorPort
import org.scaleadvisor.backend.project.domain.ProjectFactor
import org.scaleadvisor.backend.project.domain.enum.CocomoType
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
private class ProjectFactorJooqAdapter(
    private val dsl: DSLContext
) : CreateProjectFactorPort, GetProjectFactorPort {

    private fun ProjectFactorRecord.toDomain() = ProjectFactor(
        projectId = ProjectId.of(this.projectId),
        unitCost = this.unitCost,
        teamSize = this.teamSize,
        cocomoType = CocomoType.valueOf(this.cocomoType),
    )

    override fun create(projectFactor: ProjectFactor) {
        dsl
            .insertInto(PROJECT_FACTOR)
            .set(PROJECT_FACTOR.PROJECT_FACTOR_ID, IdUtil.generateId())
            .set(PROJECT_FACTOR.PROJECT_ID, projectFactor.projectId.toLong())
            .set(PROJECT_FACTOR.UNIT_COST, projectFactor.unitCost)
            .set(PROJECT_FACTOR.TEAM_SIZE, projectFactor.teamSize)
            .set(PROJECT_FACTOR.COCOMO_TYPE, projectFactor.cocomoType.name)
            .set(PROJECT_FACTOR.CREATED_AT, LocalDateTime.now())
            .set(PROJECT_FACTOR.UPDATED_AT, LocalDateTime.now())
            .execute()
    }

    override fun find(projectId: ProjectId): ProjectFactor? {
        return dsl
            .selectFrom(PROJECT_FACTOR)
            .where(PROJECT_FACTOR.PROJECT_ID.eq(projectId.toLong()))
            .fetchOne { record -> record.into(PROJECT_FACTOR).toDomain() }
    }

}