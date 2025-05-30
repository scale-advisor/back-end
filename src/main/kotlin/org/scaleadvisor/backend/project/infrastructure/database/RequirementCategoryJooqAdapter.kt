package org.scaleadvisor.backend.project.infrastructure.database

import ProjectVersionId
import org.jooq.DSLContext
import org.jooq.generated.Tables.REQUIREMENT_CATEGORY
import org.jooq.generated.tables.records.RequirementCategoryRecord
import org.scaleadvisor.backend.global.util.IdUtil
import org.scaleadvisor.backend.project.application.port.repository.requirementcategory.CreateRequirementCategoryPort
import org.scaleadvisor.backend.project.application.port.repository.requirementcategory.DeleteRequirementCategoryPort
import org.scaleadvisor.backend.project.application.port.repository.requirementcategory.GetRequirementCategoryPort
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.RequirementCategory
import org.scaleadvisor.backend.project.domain.enum.RequirementCategoryName
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.id.RequirementCategoryId
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
private class RequirementCategoryAdapter(
    private val dsl: DSLContext
) : CreateRequirementCategoryPort, GetRequirementCategoryPort, DeleteRequirementCategoryPort {

    private fun RequirementCategoryRecord.toDomain() = RequirementCategory(
        id = RequirementCategoryId.from(this.requirementCategoryId),
        projectVersionId = ProjectVersionId.of(
            projectId = ProjectId.from(this.projectId),
            major = this.versionMajorNumber,
            minor = this.versionMinorNumber,
        ),
        name = RequirementCategoryName.valueOf(this.requirementCategoryName),
        prefix = this.requirementCategoryPrefix,
    )

    override fun createAll(requirementCategoryList: List<RequirementCategory>) {
        val insertStep = dsl
            .insertInto(
                REQUIREMENT_CATEGORY,
                REQUIREMENT_CATEGORY.REQUIREMENT_CATEGORY_ID,
                REQUIREMENT_CATEGORY.PROJECT_ID,
                REQUIREMENT_CATEGORY.VERSION_MAJOR_NUMBER,
                REQUIREMENT_CATEGORY.VERSION_MINOR_NUMBER,
                REQUIREMENT_CATEGORY.REQUIREMENT_CATEGORY_NAME,
                REQUIREMENT_CATEGORY.REQUIREMENT_CATEGORY_PREFIX,
                REQUIREMENT_CATEGORY.CREATED_AT,
                REQUIREMENT_CATEGORY.UPDATED_AT,
            )
            .apply {
                requirementCategoryList.forEach { requirementCategory ->
                    values(
                        IdUtil.generateId(),
                        requirementCategory.projectVersionId.projectId.toLong(),
                        requirementCategory.projectVersionId.major,
                        requirementCategory.projectVersionId.minor,
                        requirementCategory.name.name,
                        requirementCategory.prefix,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                    )
                }
            }

        insertStep.execute()
    }

    override fun findAll(projectVersion: ProjectVersion): List<RequirementCategory> {
        return dsl.selectFrom(REQUIREMENT_CATEGORY)
            .where(REQUIREMENT_CATEGORY.PROJECT_ID.eq(projectVersion.projectId.toLong()))
            .and(REQUIREMENT_CATEGORY.VERSION_MAJOR_NUMBER.eq(projectVersion.major))
            .and(REQUIREMENT_CATEGORY.VERSION_MINOR_NUMBER.eq(projectVersion.minor))
            .fetch { record -> record.into(REQUIREMENT_CATEGORY).toDomain() }
    }

    override fun deleteAll(projectId: ProjectId) {
        dsl.deleteFrom(REQUIREMENT_CATEGORY)
            .where(REQUIREMENT_CATEGORY.PROJECT_ID.eq(projectId.toLong()))
            .execute()
    }

    override fun deleteAll(
        projectId: ProjectId,
        versionMajorNumber: Int
    ) {
        dsl.deleteFrom(REQUIREMENT_CATEGORY)
            .where(REQUIREMENT_CATEGORY.PROJECT_ID.eq(projectId.toLong()))
            .and(REQUIREMENT_CATEGORY.VERSION_MAJOR_NUMBER.eq(versionMajorNumber))
            .execute()
    }

    override fun deleteAll(projectVersion: ProjectVersion) {
        dsl.deleteFrom(REQUIREMENT_CATEGORY)
            .where(REQUIREMENT_CATEGORY.PROJECT_ID.eq(projectVersion.projectId.toLong()))
            .and(REQUIREMENT_CATEGORY.VERSION_MAJOR_NUMBER.eq(projectVersion.major))
            .and(REQUIREMENT_CATEGORY.VERSION_MINOR_NUMBER.eq(projectVersion.minor))
            .execute()
    }

}