package org.scaleadvisor.backend.project.infrastructure.database

import ProjectVersionId
import org.jooq.DSLContext
import org.jooq.generated.Tables.REQUIREMENT
import org.jooq.generated.tables.records.RequirementRecord
import org.jooq.impl.DSL
import org.scaleadvisor.backend.global.util.IdUtil
import org.scaleadvisor.backend.project.application.port.repository.requirement.CreateRequirementPort
import org.scaleadvisor.backend.project.application.port.repository.requirement.DeleteRequirementPort
import org.scaleadvisor.backend.project.application.port.repository.requirement.GetRequirementPort
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.Requirement
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
private class RequirementJooqAdapter(
    private val dsl: DSLContext
) : CreateRequirementPort, GetRequirementPort, DeleteRequirementPort {

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
        detail = this.requirementDetail,
        type = this.requirementType,
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
                REQUIREMENT.REQUIREMENT_DETAIL,
                REQUIREMENT.REQUIREMENT_TYPE,
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
                        requirement.detail,
                        requirement.type,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                    )
                }
            }

        insertStep.execute()
    }

    override fun findAll(projectVersion: ProjectVersion): List<Requirement> {
        return dsl.selectFrom(REQUIREMENT)
            .where(REQUIREMENT.PROJECT_ID.eq(projectVersion.projectId.toLong()))
            .and(REQUIREMENT.VERSION_MAJOR_NUMBER.eq(projectVersion.major))
            .and(REQUIREMENT.VERSION_MINOR_NUMBER.eq(projectVersion.minor))
            .fetch { record -> record.into(REQUIREMENT).toDomain() }
    }

    override fun findAll(
        projectVersion: ProjectVersion,
        requirementNumberPrefixList: List<String>
    ): List<Requirement> {
        if (requirementNumberPrefixList.isEmpty()) {
            return emptyList()
        }

        val distinctPrefixes = requirementNumberPrefixList.distinct()
        val likeConditions = distinctPrefixes.map { prefix ->
            REQUIREMENT.REQUIREMENT_NUMBER.like("$prefix%")
        }

        return dsl.selectFrom(REQUIREMENT)
            .where(REQUIREMENT.PROJECT_ID.eq(projectVersion.projectId.toLong()))
            .and(REQUIREMENT.VERSION_MAJOR_NUMBER.eq(projectVersion.major))
            .and(REQUIREMENT.VERSION_MINOR_NUMBER.eq(projectVersion.minor))
            .and(DSL.or(likeConditions))
            .orderBy(REQUIREMENT.REQUIREMENT_NUMBER.asc())
            .fetch { record -> record.into(REQUIREMENT).toDomain() }
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