package org.scaleadvisor.backend.project.infrastructure

import org.jooq.DSLContext
import org.jooq.generated.Tables.PROJECT_LANGUAGE
import org.scaleadvisor.backend.global.util.IdUtil
import org.scaleadvisor.backend.project.application.port.repository.projectlanguage.CreateProjectLanguagePort
import org.scaleadvisor.backend.project.domain.ProjectLanguage
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
private class ProjectLanguageJooqAdapter(
    private val dsl: DSLContext
) : CreateProjectLanguagePort {

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

}