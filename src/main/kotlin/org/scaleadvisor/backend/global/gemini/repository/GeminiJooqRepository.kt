package org.scaleadvisor.backend.global.gemini.repository

import ProjectVersionId
import org.jooq.DSLContext
import org.jooq.generated.Tables.*
import org.jooq.generated.tables.records.RequirementCategoryRecord
import org.jooq.generated.tables.records.RequirementRecord
import org.jooq.generated.tables.records.UnitProcessRecord
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.global.gemini.data.UnitProcessResponse
import org.scaleadvisor.backend.global.util.IdUtil
import org.scaleadvisor.backend.project.domain.AdjustmentFactor
import org.scaleadvisor.backend.project.domain.enum.AdjustmentFactorType
import org.scaleadvisor.backend.project.domain.enum.RequirementCategoryName
import org.scaleadvisor.backend.project.domain.id.AdjustmentFactorId
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class GeminiJooqRepository(
    private val dsl: DSLContext
) {
    fun findAllByProject(projectId: Long): List<RequirementRecord> =
        dsl.select(
            REQUIREMENT.REQUIREMENT_ID,
            REQUIREMENT.PROJECT_ID,
            REQUIREMENT.REQUIREMENT_NUMBER,
            REQUIREMENT.REQUIREMENT_TYPE,
            REQUIREMENT.REQUIREMENT_NAME,
            REQUIREMENT.REQUIREMENT_DEFINITION,
            REQUIREMENT.REQUIREMENT_DETAIL,
        )
            .from(REQUIREMENT)
            .where(REQUIREMENT.PROJECT_ID.eq(projectId))
            .orderBy(
                REQUIREMENT.VERSION_MAJOR_NUMBER.asc(),
                REQUIREMENT.VERSION_MINOR_NUMBER.asc(),
                REQUIREMENT.REQUIREMENT_ID.asc()
            )
            .fetchInto(RequirementRecord::class.java)

    fun saveUnitProcess(
        dto: List<UnitProcessResponse>,
        reqMap: Map<String, Long>
    ) {
        val now = LocalDateTime.now()

        dto.forEach { d ->
            val unitProcessId = IdUtil.generateId()

            dsl.insertInto(UNIT_PROCESS)
                .set(UNIT_PROCESS.UNIT_PROCESS_ID,   unitProcessId)
                .set(UNIT_PROCESS.UNIT_PROCESS_NAME, d.unitProcess)
                .set(UNIT_PROCESS.FUNCTION_TYPE,     "UNDEFINED")
                .set(UNIT_PROCESS.CREATED_AT,        now)
                .set(UNIT_PROCESS.UPDATED_AT,        now)
                .set(UNIT_PROCESS.IS_AMBIGUOUS,      0)
                .execute()

            d.ids
                .flatMap {
                    it.split(",")
                        .map { id -> id.trim() }
                }
                .distinct()
                .forEach { detailNum ->
                    reqMap[detailNum]?.let { requirementId ->
                        dsl.insertInto(REQUIREMENT_UNIT_PROCESS)
                            .set(REQUIREMENT_UNIT_PROCESS.REQUIREMENT_ID,  requirementId)
                            .set(REQUIREMENT_UNIT_PROCESS.UNIT_PROCESS_ID, unitProcessId)
                            .set(REQUIREMENT_UNIT_PROCESS.CREATED_AT,      now)
                            .set(REQUIREMENT_UNIT_PROCESS.UPDATED_AT,      now)
                            .execute()
                    }
                }
        }
    }

    fun findAllUnitProcessesByProject(projectId: Long): List<UnitProcessRecord> =
        dsl.select(
            UNIT_PROCESS.UNIT_PROCESS_ID,
            UNIT_PROCESS.UNIT_PROCESS_NAME,
            UNIT_PROCESS.FUNCTION_TYPE,
            UNIT_PROCESS.CREATED_AT,
            UNIT_PROCESS.UPDATED_AT,
            UNIT_PROCESS.IS_AMBIGUOUS
        )
            .from(UNIT_PROCESS)

            .join(REQUIREMENT_UNIT_PROCESS)
            .on(UNIT_PROCESS.UNIT_PROCESS_ID.eq(REQUIREMENT_UNIT_PROCESS.UNIT_PROCESS_ID))
            .join(REQUIREMENT)
            .on(REQUIREMENT_UNIT_PROCESS.REQUIREMENT_ID.eq(REQUIREMENT.REQUIREMENT_ID))
            .where(REQUIREMENT.PROJECT_ID.eq(projectId))
            .fetchInto(UnitProcessRecord::class.java)

    fun markAsAmbiguous(unitProcessIds: List<String>): Int {
        if (unitProcessIds.isEmpty()) return 0

        return dsl.update(UNIT_PROCESS)
            .set(UNIT_PROCESS.IS_AMBIGUOUS, 1.toByte())
            .where(UNIT_PROCESS.UNIT_PROCESS_ID.`in`(unitProcessIds))
            .execute()
    }

    fun listRequirementsByPrefix(projectId: Long, prefix: String): List<String> {
        return dsl.select(
            REQUIREMENT.REQUIREMENT_NUMBER,
            REQUIREMENT.REQUIREMENT_NAME,
            REQUIREMENT.REQUIREMENT_DETAIL
        )
            .from(REQUIREMENT)
            .where(REQUIREMENT.PROJECT_ID.eq(projectId))
            .and(REQUIREMENT.REQUIREMENT_NUMBER.like("$prefix%"))
            .orderBy(REQUIREMENT.REQUIREMENT_NUMBER.asc())
            .fetch { r ->
                "${r.get(REQUIREMENT.REQUIREMENT_NUMBER)}: ${r.get(REQUIREMENT.REQUIREMENT_DETAIL)}"
            }
    }

    fun saveAdjustmentFactors(
        projectVersionId: ProjectVersionId,
        factors: List<AdjustmentFactor>
    ): Int {
        if (factors.isEmpty()) return 0

        val now = LocalDateTime.now()
        val insert = dsl.insertInto(
            ADJUSTMENT_FACTOR,
            ADJUSTMENT_FACTOR.ADJUSTMENT_FACTOR_ID,
            ADJUSTMENT_FACTOR.PROJECT_ID,
            ADJUSTMENT_FACTOR.VERSION_MAJOR_NUMBER,
            ADJUSTMENT_FACTOR.VERSION_MINOR_NUMBER,
            ADJUSTMENT_FACTOR.ADJUSTMENT_FACTOR_TYPE,
            ADJUSTMENT_FACTOR.ADJUSTMENT_FACTOR_LEVEL,
            ADJUSTMENT_FACTOR.CREATED_AT,
            ADJUSTMENT_FACTOR.UPDATED_AT
        )

        factors.forEach { factor ->
            insert.values(
                factor.id.toLong(),
                projectVersionId.projectId.toLong(),
                projectVersionId.major,
                projectVersionId.minor,
                factor.type.name,
                factor.level,
                now,
                now
            )
        }

        return insert.execute()
    }

    fun findAllCategories(projectId: Long): List<RequirementCategoryRecord> =
        dsl.select(
            REQUIREMENT_CATEGORY.REQUIREMENT_CATEGORY_ID,
            REQUIREMENT_CATEGORY.PROJECT_ID,
            REQUIREMENT_CATEGORY.VERSION_MAJOR_NUMBER,
            REQUIREMENT_CATEGORY.VERSION_MINOR_NUMBER,
            REQUIREMENT_CATEGORY.REQUIREMENT_CATEGORY_NAME,
            REQUIREMENT_CATEGORY.REQUIREMENT_CATEGORY_PREFIX
        )
            .from(REQUIREMENT_CATEGORY)
            .where(REQUIREMENT_CATEGORY.PROJECT_ID.eq(projectId))
            .fetchInto(RequirementCategoryRecord::class.java)
}