package org.scaleadvisor.backend.project.infrastructure

import org.jooq.DSLContext
import org.jooq.generated.Tables.PROJECT_FACTOR
import org.scaleadvisor.backend.project.application.port.repository.projectfactor.CreateProjectFactorPort
import org.scaleadvisor.backend.project.domain.ProjectFactor
import org.springframework.stereotype.Repository

@Repository
private class ProjectFactorJooqAdapter(
    private val dsl: DSLContext
) : CreateProjectFactorPort {
    override fun create(projectFactor: ProjectFactor) {
        dsl
            .insertInto(PROJECT_FACTOR)
            .set(PROJECT_FACTOR.PROJECT_FACTOR_ID, projectFactor.id.toLong())
            .set(PROJECT_FACTOR.PROJECT_ID, projectFactor.projectId.toLong())
            .set(PROJECT_FACTOR.UNIT_COST, projectFactor.unitCost)
            .set(PROJECT_FACTOR.TEAM_SIZE, projectFactor.teamSize)
            .set(PROJECT_FACTOR.COCOMO_TYPE, projectFactor.cocomoType.name)
            .set(PROJECT_FACTOR.CREATED_AT, projectFactor.createdAt)
            .set(PROJECT_FACTOR.UPDATED_AT, projectFactor.updatedAt!!)
            .execute()
    }
}