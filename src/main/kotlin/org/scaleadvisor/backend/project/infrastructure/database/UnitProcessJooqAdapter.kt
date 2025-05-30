package org.scaleadvisor.backend.project.infrastructure.database

import org.jooq.DSLContext
import org.jooq.generated.Tables.*
import org.jooq.generated.tables.records.UnitProcessRecord
import org.scaleadvisor.backend.project.application.port.repository.unitprocess.CreateUnitProcessPort
import org.scaleadvisor.backend.project.application.port.repository.unitprocess.DeleteUnitProcessPort
import org.scaleadvisor.backend.project.application.port.repository.unitprocess.GetUnitProcessPort
import org.scaleadvisor.backend.project.application.port.repository.unitprocess.UpdateUnitProcessPort
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.scaleadvisor.backend.project.domain.enum.FunctionType
import org.scaleadvisor.backend.project.domain.id.UnitProcessId
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
private class UnitProcessJooqAdapter(
    private val dsl: DSLContext
) : CreateUnitProcessPort, GetUnitProcessPort, UpdateUnitProcessPort, DeleteUnitProcessPort {

    private fun toByte(isAmbiguous: Boolean): Byte {
        return if (isAmbiguous) 1 else 0
    }
    private fun toBoolean(isAmbiguous: Byte): Boolean {
        return isAmbiguous == 1.toByte()
    }
    private fun UnitProcessRecord.toDomain() = UnitProcess(
        id = UnitProcessId.from(this.unitProcessId),
        name = this.unitProcessName,
        functionType = FunctionType.valueOf(this.functionType),
        isAmbiguous = toBoolean(isAmbiguous),
    )


    override fun createAll(unitProcessList: List<UnitProcess>){
        val now = LocalDateTime.now()

        val insertStep = dsl
            .insertInto(
                UNIT_PROCESS,
                UNIT_PROCESS.UNIT_PROCESS_ID,
                UNIT_PROCESS.UNIT_PROCESS_NAME,
                UNIT_PROCESS.FUNCTION_TYPE,
                UNIT_PROCESS.IS_AMBIGUOUS,
                UNIT_PROCESS.CREATED_AT,
                UNIT_PROCESS.UPDATED_AT,
            )
            .apply {
                unitProcessList.forEach { unitProcess ->
                    values(
                        unitProcess.id.toLong(),
                        unitProcess.name,
                        unitProcess.functionType.name,
                        toByte(unitProcess.isAmbiguous),
                        now,
                        now,
                    )
                }
            }

        insertStep.execute()
    }

    override fun findAll(projectVersion: ProjectVersion): List<UnitProcess> {
        return dsl.select(
            UNIT_PROCESS.UNIT_PROCESS_ID,
            UNIT_PROCESS.UNIT_PROCESS_NAME,
            UNIT_PROCESS.FUNCTION_TYPE,
            UNIT_PROCESS.IS_AMBIGUOUS,
            UNIT_PROCESS.CREATED_AT,
            UNIT_PROCESS.UPDATED_AT
        )
            .from(UNIT_PROCESS)
            .join(REQUIREMENT_UNIT_PROCESS)
            .on(UNIT_PROCESS.UNIT_PROCESS_ID.eq(REQUIREMENT_UNIT_PROCESS.UNIT_PROCESS_ID))
            .join(REQUIREMENT)
            .on(REQUIREMENT_UNIT_PROCESS.REQUIREMENT_ID.eq(REQUIREMENT.REQUIREMENT_ID))

            .where(REQUIREMENT.PROJECT_ID.eq(projectVersion.projectId.toLong()))
            .and(REQUIREMENT.VERSION_MAJOR_NUMBER.eq(projectVersion.major))
            .and(REQUIREMENT.VERSION_MINOR_NUMBER.eq(projectVersion.minor))
            .fetch{record -> record.into(UNIT_PROCESS).toDomain()}
    }

    override fun updateAll(unitProcessList: List<UnitProcess>) {
        dsl.batchUpdate(unitProcessList.map {
            dsl.newRecord(UNIT_PROCESS).apply {
                unitProcessId = it.id.toLong()
                unitProcessName = it.name
                functionType = it.functionType.name
                isAmbiguous = 1.toByte()
                updatedAt = LocalDateTime.now()
            }
        }).execute()
    }

    override fun updateAllIsAmbiguous(unitProcessIdList: List<UnitProcessId>) {
        dsl.batchUpdate(unitProcessIdList.map {
            dsl.newRecord(UNIT_PROCESS).apply {
                unitProcessId = it.toLong()
                isAmbiguous = 1.toByte()
            }
        }).execute()
    }

    override fun deleteAll(unitProcessIdList: List<UnitProcessId>) {
        dsl.deleteFrom(UNIT_PROCESS)
            .where(UNIT_PROCESS.UNIT_PROCESS_ID.`in`(unitProcessIdList.map { it.toLong() }))
            .execute()
    }

}