package org.scaleadvisor.backend.project.infrastructure

import org.jooq.DSLContext
import org.jooq.generated.Tables.PROJECT_LANGUAGE
import org.jooq.generated.tables.records.ProjectLanguageRecord
import org.scaleadvisor.backend.global.util.IdUtil
import org.scaleadvisor.backend.project.application.port.repository.projectlanguage.CreateProjectLanguagePort
import org.scaleadvisor.backend.project.application.port.repository.projectlanguage.GetProjectLanguagePort
import org.scaleadvisor.backend.project.domain.ProjectLanguage
import org.scaleadvisor.backend.project.domain.enum.ProgramLanguage
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
private class ProjectLanguageJooqAdapter(
    private val dsl: DSLContext
) : CreateProjectLanguagePort, GetProjectLanguagePort {

    private fun ProjectLanguageRecord.toDomain() = ProjectLanguage(
        projectId = ProjectId.of(this.projectId),
        language = ProgramLanguage.valueOf(this.language),
        rate = this.rate
    )

    override fun createAll(projectLanguageList: List<ProjectLanguage>) {
        val insertStep = dsl
            .insertInto(PROJECT_LANGUAGE,
                PROJECT_LANGUAGE.PROJECT_LANGUAGE_ID,
                PROJECT_LANGUAGE.PROJECT_ID,
                PROJECT_LANGUAGE.LANGUAGE,
                PROJECT_LANGUAGE.RATE,
                PROJECT_LANGUAGE.CREATED_AT,
                PROJECT_LANGUAGE.UPDATED_AT
            )
            .apply {
                projectLanguageList.forEach { projectLanguage ->
                    values(
                        IdUtil.generateId(),
                        projectLanguage.projectId.toLong(),
                        projectLanguage.language.name,
                        projectLanguage.rate,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                    )
                }
            }

        insertStep.execute()
    }

    override fun findAll(projectId: ProjectId): List<ProjectLanguage> {
        return dsl.selectFrom(PROJECT_LANGUAGE)
           .where(PROJECT_LANGUAGE.PROJECT_ID.eq(projectId.toLong()))
           .fetch { record -> record.into(PROJECT_LANGUAGE).toDomain() }
    }

}