package org.scaleadvisor.backend.project.infrastructure.database

import org.jooq.DSLContext
import org.jooq.generated.Tables.REQUIREMENT_UNIT_PROCESS
import org.scaleadvisor.backend.project.application.port.repository.requirementunitprocess.CreateRequirementUnitProcessPort
import org.scaleadvisor.backend.project.domain.RequirementUnitProcess
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
private class RequirementUnitProcessJooqAdapter(
    private val dsl: DSLContext
) : CreateRequirementUnitProcessPort {
    override fun createAll(requirementUnitProcessList: List<RequirementUnitProcess>) {
        val now = LocalDateTime.now()

        val insertStep = dsl.insertInto(
            REQUIREMENT_UNIT_PROCESS,
            REQUIREMENT_UNIT_PROCESS.REQUIREMENT_ID,
            REQUIREMENT_UNIT_PROCESS.UNIT_PROCESS_ID,
            REQUIREMENT_UNIT_PROCESS.CREATED_AT,
            REQUIREMENT_UNIT_PROCESS.UPDATED_AT,
        ).apply {
            requirementUnitProcessList.forEach { requirementUnitProcess ->
                values(
                    requirementUnitProcess.requirementId.toLong(),
                    requirementUnitProcess.unitProcessId.toLong(),
                    now,
                    now,
                )
            }
        }

        insertStep.execute()
    }

}