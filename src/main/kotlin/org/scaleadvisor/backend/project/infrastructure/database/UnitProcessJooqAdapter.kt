package org.scaleadvisor.backend.project.infrastructure.database

import ProjectVersionId
import org.jooq.DSLContext
import org.jooq.generated.Tables.REQUIREMENT_UNIT_PROCESS
import org.jooq.generated.Tables.UNIT_PROCESS
import org.jooq.generated.tables.records.UnitProcessRecord
import org.jooq.impl.DSL
import org.scaleadvisor.backend.project.application.port.repository.unitprocess.CreateUnitProcessPort
import org.scaleadvisor.backend.project.application.port.repository.unitprocess.DeleteUnitProcessPort
import org.scaleadvisor.backend.project.application.port.repository.unitprocess.GetUnitProcessPort
import org.scaleadvisor.backend.project.application.port.repository.unitprocess.UpdateUnitProcessPort
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.scaleadvisor.backend.project.domain.enum.FunctionType
import org.scaleadvisor.backend.project.domain.id.ProjectId
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
        projectVersionId = ProjectVersionId.of(
            ProjectId.from(this.projectId),
            this.versionMajorNumber,
            this.versionMinorNumber,
        ),
        name = this.unitProcessName,
        functionType = FunctionType.valueOf(this.functionType),
        isAmbiguous = toBoolean(isAmbiguous),
    )

    private fun UnitProcessRecord.getId() = UnitProcessId.from(this.unitProcessId)


    override fun createAll(unitProcessList: List<UnitProcess>) {
        val now = LocalDateTime.now()

        val insertStep = dsl
            .insertInto(
                UNIT_PROCESS,
                UNIT_PROCESS.UNIT_PROCESS_ID,
                UNIT_PROCESS.PROJECT_ID,
                UNIT_PROCESS.VERSION_MAJOR_NUMBER,
                UNIT_PROCESS.VERSION_MINOR_NUMBER,
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
                        unitProcess.projectVersionId.projectId.toLong(),
                        unitProcess.projectVersionId.major,
                        unitProcess.projectVersionId.minor,
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
        return dsl.selectFrom(UNIT_PROCESS)
            .where(UNIT_PROCESS.PROJECT_ID.eq(projectVersion.projectId.toLong()))
            .and(UNIT_PROCESS.VERSION_MAJOR_NUMBER.eq(projectVersion.major))
            .and(UNIT_PROCESS.VERSION_MINOR_NUMBER.eq(projectVersion.minor))
            .fetch { record -> record.into(UNIT_PROCESS).toDomain() }
    }

    override fun findAllId(projectVersion: ProjectVersion): List<UnitProcessId> {
        return dsl.selectFrom(UNIT_PROCESS)
            .where(UNIT_PROCESS.PROJECT_ID.eq(projectVersion.projectId.toLong()))
            .and(UNIT_PROCESS.VERSION_MAJOR_NUMBER.eq(projectVersion.major))
            .and(UNIT_PROCESS.VERSION_MINOR_NUMBER.eq(projectVersion.minor))
            .fetch { record -> record.into(UNIT_PROCESS).getId() }
    }

    override fun updateAll(unitProcessList: List<UnitProcess>) {
        dsl.batchUpdate(unitProcessList.map {
            dsl.newRecord(UNIT_PROCESS).apply {
                unitProcessId = it.id.toLong()
                projectId = it.projectVersionId.projectId.toLong()
                versionMajorNumber = it.projectVersionId.major
                versionMinorNumber = it.projectVersionId.minor
                unitProcessName = it.name
                functionType = it.functionType.name
                isAmbiguous = toByte(it.isAmbiguous)
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

    override fun deleteAll(projectId: ProjectId) {
        dsl.transaction { configuration ->
            val ctx = DSL.using(configuration)

            ctx.deleteFrom(REQUIREMENT_UNIT_PROCESS)
                .where(REQUIREMENT_UNIT_PROCESS.UNIT_PROCESS_ID.`in`(
                    ctx.select(UNIT_PROCESS.UNIT_PROCESS_ID)
                        .from(UNIT_PROCESS)
                        .where(UNIT_PROCESS.PROJECT_ID.eq(projectId.toLong()))
                ))
                .execute()

            ctx.deleteFrom(UNIT_PROCESS)
                .where(UNIT_PROCESS.PROJECT_ID.eq(projectId.toLong()))
                .execute()
        }
    }

    override fun deleteAll(
        projectId: ProjectId,
        versionMajorNumber: Int
    ) {
        dsl.transaction { configuration ->
            val ctx = DSL.using(configuration)

            ctx.deleteFrom(REQUIREMENT_UNIT_PROCESS)
                .where(REQUIREMENT_UNIT_PROCESS.UNIT_PROCESS_ID.`in`(
                    ctx.select(UNIT_PROCESS.UNIT_PROCESS_ID)
                        .from(UNIT_PROCESS)
                        .where(UNIT_PROCESS.PROJECT_ID.eq(projectId.toLong()))
                        .and(UNIT_PROCESS.VERSION_MAJOR_NUMBER.eq(versionMajorNumber))
                ))
                .execute()

            ctx.deleteFrom(UNIT_PROCESS)
                .where(UNIT_PROCESS.PROJECT_ID.eq(projectId.toLong()))
                .and(UNIT_PROCESS.VERSION_MAJOR_NUMBER.eq(versionMajorNumber))
                .execute()
        }
    }

    override fun deleteAll(projectVersion: ProjectVersion) {
        dsl.transaction { configuration ->
            val ctx = DSL.using(configuration)

            ctx.deleteFrom(REQUIREMENT_UNIT_PROCESS)
                .where(REQUIREMENT_UNIT_PROCESS.UNIT_PROCESS_ID.`in`(
                    ctx.select(UNIT_PROCESS.UNIT_PROCESS_ID)
                        .from(UNIT_PROCESS)
                        .where(UNIT_PROCESS.PROJECT_ID.eq(projectVersion.projectId.toLong()))
                        .and(UNIT_PROCESS.VERSION_MAJOR_NUMBER.eq(projectVersion.major))
                        .and(UNIT_PROCESS.VERSION_MINOR_NUMBER.eq(projectVersion.minor))
                ))
                .execute()

            ctx.deleteFrom(UNIT_PROCESS)
                .where(UNIT_PROCESS.PROJECT_ID.eq(projectVersion.projectId.toLong()))
                .and(UNIT_PROCESS.VERSION_MAJOR_NUMBER.eq(projectVersion.major))
                .and(UNIT_PROCESS.VERSION_MINOR_NUMBER.eq(projectVersion.minor))
                .execute()
        }
    }

    override fun deleteAll(unitProcessIdList: List<UnitProcessId>) {
        dsl.transaction { configuration ->
            val ctx = DSL.using(configuration)

            ctx.deleteFrom(REQUIREMENT_UNIT_PROCESS)
                .where(REQUIREMENT_UNIT_PROCESS.UNIT_PROCESS_ID.`in`(
                    ctx.select(UNIT_PROCESS.UNIT_PROCESS_ID)
                        .from(UNIT_PROCESS)
                        .where(UNIT_PROCESS.UNIT_PROCESS_ID.`in`(unitProcessIdList.map { it.toLong() }))
                ))
                .execute()

            ctx.deleteFrom(UNIT_PROCESS)
                .where(UNIT_PROCESS.UNIT_PROCESS_ID.`in`(unitProcessIdList.map { it.toLong() }))
                .execute()
        }
    }

}