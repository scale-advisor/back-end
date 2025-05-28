package org.scaleadvisor.backend.global.gemini.repository

import org.jooq.DSLContext
import org.jooq.generated.Tables.*
import org.jooq.generated.tables.records.RequirementRecord
import org.jooq.generated.tables.records.UnitProcessRecord
import org.scaleadvisor.backend.global.gemini.data.UnitProcessResponse
import org.scaleadvisor.backend.global.util.IdUtil
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
}