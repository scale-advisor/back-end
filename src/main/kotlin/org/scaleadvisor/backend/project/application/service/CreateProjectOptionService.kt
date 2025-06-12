package org.scaleadvisor.backend.project.application.service

import org.scaleadvisor.backend.project.application.port.usecase.CreateProjectOptionUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectfactor.CreateProjectFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectlanguage.CreateProjectLanguageUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class CreateProjectOptionService(
    private val createProjectFactorUseCase: CreateProjectFactorUseCase,
    private val createProjectLanguageUseCase: CreateProjectLanguageUseCase,
) : CreateProjectOptionUseCase {
    override fun create(command: CreateProjectOptionUseCase.Command) {
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