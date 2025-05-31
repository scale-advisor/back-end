package org.scaleadvisor.backend.project.infrastructure.database

import org.jooq.DSLContext
import org.jooq.generated.Tables.REQUIREMENT_UNIT_PROCESS
import org.scaleadvisor.backend.global.util.IdUtil
import org.scaleadvisor.backend.project.application.port.repository.requirementunitprocess.CreateRequirementUnitProcessPort
import org.scaleadvisor.backend.project.application.port.repository.requirementunitprocess.DeleteRequirementUnitProcessPort
import org.scaleadvisor.backend.project.application.port.repository.requirementunitprocess.GetRequirementUnitProcessPort
import org.scaleadvisor.backend.project.domain.RequirementUnitProcess
import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.scaleadvisor.backend.project.domain.id.UnitProcessId
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
private class RequirementUnitProcessJooqAdapter(
    private val dsl: DSLContext
) : CreateRequirementUnitProcessPort, GetRequirementUnitProcessPort, DeleteRequirementUnitProcessPort {



    override fun createAll(requirementUnitProcessList: List<RequirementUnitProcess>) {
        val now = LocalDateTime.now()

        val insertStep = dsl.insertInto(
            REQUIREMENT_UNIT_PROCESS,
            REQUIREMENT_UNIT_PROCESS.REQUIREMENT_UNIT_PROCESS_ID,
            REQUIREMENT_UNIT_PROCESS.REQUIREMENT_ID,
            REQUIREMENT_UNIT_PROCESS.UNIT_PROCESS_ID,
            REQUIREMENT_UNIT_PROCESS.CREATED_AT,
            REQUIREMENT_UNIT_PROCESS.UPDATED_AT,
        ).apply {
            requirementUnitProcessList.forEach { requirementUnitProcess ->
                values(
                    IdUtil.generateId(),
                    requirementUnitProcess.requirementId.toLong(),
                    requirementUnitProcess.unitProcessId.toLong(),
                    now,
                    now,
                )
            }
        }

        insertStep.execute()
    }

    override fun findAllDistinctUnitProcessId(requirementIdList: List<RequirementId>): List<UnitProcessId> {
        return dsl.selectDistinct(REQUIREMENT_UNIT_PROCESS.UNIT_PROCESS_ID)
            .from(REQUIREMENT_UNIT_PROCESS)
            .where(REQUIREMENT_UNIT_PROCESS.REQUIREMENT_ID.`in`(requirementIdList.map {it.toLong() }))
            .fetch{record -> UnitProcessId.from(record.into(REQUIREMENT_UNIT_PROCESS).unitProcessId)}
    }

    override fun deleteAll(requirementIdList: List<RequirementId>) {
        dsl.deleteFrom(REQUIREMENT_UNIT_PROCESS)
            .where(REQUIREMENT_UNIT_PROCESS.REQUIREMENT_ID.`in`(requirementIdList.map { it.toLong() }))
            .execute()
    }

}