package org.scaleadvisor.backend.project.application.service.projectlanguage

import org.scaleadvisor.backend.project.application.port.repository.projectlanguage.CreateProjectLanguagePort
import org.scaleadvisor.backend.project.application.port.usecase.projectlanguage.CreateProjectLanguageUseCase
import org.scaleadvisor.backend.project.domain.ProjectLanguage
import org.scaleadvisor.backend.project.domain.enum.ProgramLanguage
import org.springframework.stereotype.Service

@Service
private class CreateProjectLanguageService(
    private val createProjectLanguagePort: CreateProjectLanguagePort
) : CreateProjectLanguageUseCase {

    override fun create(command: CreateProjectLanguageUseCase.Command) {
        val rate : Int = 100 / command.languageList.size
        val projectLanguageList = command.languageList.map {
            ProjectLanguage(
                projectId = command.projectId,
                language = ProgramLanguage.valueOf(it.uppercase()),
                rate = rate
            )
        }

        createProjectLanguagePort.createAll(projectLanguageList)
    }

}
