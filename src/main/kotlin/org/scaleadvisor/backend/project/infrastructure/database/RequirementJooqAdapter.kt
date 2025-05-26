package org.scaleadvisor.backend.project.infrastructure.database

import ProjectVersionId
import org.jooq.DSLContext
import org.jooq.generated.Tables.REQUIREMENT
import org.jooq.generated.tables.records.RequirementRecord
import org.scaleadvisor.backend.global.util.IdUtil
import org.scaleadvisor.backend.project.application.port.repository.requirement.CreateRequirementPort
import org.scaleadvisor.backend.project.application.port.repository.requirement.DeleteRequirementPort
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.Requirement
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
private class RequirementJooqAdapter(
    private val dsl: DSLContext
) : CreateRequirementPort, DeleteRequirementPort {

    private fun RequirementRecord.toDomain() = Requirement(
        id = RequirementId.from(this.requirementId),
        projectVersionId = ProjectVersionId.of(
            projectId = ProjectId.from(this.projectId),
            major = this.versionMajorNumber,
            minor = this.versionMinorNumber,
        ),
        number = this.requirementNumber,
        name = this.requirementName,
        definition = this.requirementDefinition,
        detailNumber = this.requirementDetailNumber,
        detail = this.requirementDetail,
        type = this.requirementType,
        note = this.note,
    )

    override fun createAll(requirementList: List<Requirement>) {
        val insertStep = dsl
            .insertInto(
                REQUIREMENT,
                REQUIREMENT.REQUIREMENT_ID,
                REQUIREMENT.PROJECT_ID,
                REQUIREMENT.VERSION_MAJOR_NUMBER,
                REQUIREMENT.VERSION_MINOR_NUMBER,
                REQUIREMENT.REQUIREMENT_NUMBER,
                REQUIREMENT.REQUIREMENT_NAME,
                REQUIREMENT.REQUIREMENT_DEFINITION,
                REQUIREMENT.REQUIREMENT_DETAIL_NUMBER,
                REQUIREMENT.REQUIREMENT_DETAIL,
                REQUIREMENT.REQUIREMENT_TYPE,
                REQUIREMENT.NOTE,
                REQUIREMENT.CREATED_AT,
                REQUIREMENT.UPDATED_AT
            )
            .apply {
                requirementList.forEach { requirement ->
                    values(
                        IdUtil.generateId(),
                        requirement.projectVersionId.projectId.toLong(),
                        requirement.projectVersionId.major,
                        requirement.projectVersionId.minor,
                        requirement.number,
                        requirement.name,
                        requirement.definition,
                        requirement.detailNumber,
                        requirement.detail,
                        requirement.type,
                        requirement.note,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                    )
                }
            }

        insertStep.execute()
    }

    override fun deleteAll(projectId: ProjectId) {
        dsl.deleteFrom(REQUIREMENT)
            .where(REQUIREMENT.PROJECT_ID.eq(projectId.toLong()))
            .execute()
    }

    override fun deleteAll(
        projectId: ProjectId,
        versionMajorNumber: Int
    ) {
        dsl.deleteFrom(REQUIREMENT)
            .where(REQUIREMENT.PROJECT_ID.eq(projectId.toLong()))
            .and(REQUIREMENT.VERSION_MAJOR_NUMBER.eq(versionMajorNumber))
            .execute()
    }

    override fun deleteAll(projectVersion: ProjectVersion) {
        dsl.deleteFrom(REQUIREMENT)
            .where(REQUIREMENT.PROJECT_ID.eq(projectVersion.projectId.toLong()))
            .and(REQUIREMENT.VERSION_MAJOR_NUMBER.eq(projectVersion.major))
            .and(REQUIREMENT.VERSION_MINOR_NUMBER.eq(projectVersion.minor))
            .execute()
    }

}