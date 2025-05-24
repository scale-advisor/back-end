package org.scaleadvisor.backend.project.application.service.projectlanguage

import org.scaleadvisor.backend.project.application.port.repository.projectlanguage.CreateProjectLanguagePort
import org.scaleadvisor.backend.project.application.port.usecase.projectlanguage.CreateProjectLanguageUseCase
import org.scaleadvisor.backend.project.domain.ProjectLanguage
import org.scaleadvisor.backend.project.domain.enum.ProgramLanguage
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class CreateProjectLanguageService(
    private val createProjectLanguagePort: CreateProjectLanguagePort
) : CreateProjectLanguageUseCase {

    override fun createAllWithOutRate(
        projectId: ProjectId,
        languageList: List<String>
    ) {
        val rate : Int = 100 / languageList.size
        val projectLanguageList = languageList.map {
            ProjectLanguage(
                language = ProgramLanguage.valueOf(it.uppercase()),
                rate = rate
            )
        }

        createProjectLanguagePort.createAll(
            projectId = projectId,
            projectLanguageList = projectLanguageList
        )
    }

    override fun createAll(
        projectId: ProjectId,
        projectLanguageList: List<ProjectLanguage>
    ) {
        createProjectLanguagePort.createAll(
            projectId = projectId,
            projectLanguageList = projectLanguageList
        )
    }

}
