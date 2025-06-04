package org.scaleadvisor.backend.project.application.service

import org.scaleadvisor.backend.project.application.port.usecase.CreateProjectOptionUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectfactor.CreateProjectFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectfactor.GetProjectFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectlanguage.CreateProjectLanguageUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectlanguage.GetProjectLanguageUseCase
import org.scaleadvisor.backend.project.domain.ProjectFactor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class CreateProjectOptionService(
    private val getProjectFactorUseCase: GetProjectFactorUseCase,
    private val getProjectLanguageUseCase: GetProjectLanguageUseCase,
    private val createProjectFactorUseCase: CreateProjectFactorUseCase,
    private val createProjectLanguageUseCase: CreateProjectLanguageUseCase,
) : CreateProjectOptionUseCase {
    override fun create(command: CreateProjectOptionUseCase.Command) {
        // TODO: TO REMOVE
        if (command.projectId.toLong() == 2025060423390587487) {
            val projectFactor: ProjectFactor? = getProjectFactorUseCase.find(command.projectId)
            val projectLanguageList = getProjectLanguageUseCase.findAll(command.projectId)

            if (projectFactor == null) {
                createProjectFactorUseCase.create(
                    CreateProjectFactorUseCase.Command(
                        projectId = command.projectId,
                        unitCost = command.unitCost,
                        teamSize = command.teamSize,
                        cocomoType = command.cocomoType
                    )
                )
            }

            if (projectLanguageList.isEmpty()){
                createProjectLanguageUseCase.createAllWithOutRate(
                    projectId = command.projectId,
                    languageList = command.languageList
                )
            }
            return
        }


        createProjectFactorUseCase.create(
            CreateProjectFactorUseCase.Command(
                projectId = command.projectId,
                unitCost = command.unitCost,
                teamSize = command.teamSize,
                cocomoType = command.cocomoType
            )
        )

        createProjectLanguageUseCase.createAllWithOutRate(
            projectId = command.projectId,
            languageList = command.languageList
        )
    }
}