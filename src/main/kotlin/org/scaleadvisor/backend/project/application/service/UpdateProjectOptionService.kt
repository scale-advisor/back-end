package org.scaleadvisor.backend.project.application.service

import org.scaleadvisor.backend.project.application.port.usecase.UpdateProjectOptionUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectfactor.UpdateProjectFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectlanguage.UpdateProjectLanguageUseCase
import org.scaleadvisor.backend.project.domain.ProjectFactor
import org.springframework.stereotype.Service

@Service
class UpdateProjectOptionService(
    private val updateProjectFactorUseCase: UpdateProjectFactorUseCase,
    private val updateProjectLanguageUseCase: UpdateProjectLanguageUseCase,
) : UpdateProjectOptionUseCase {
    override fun update(command: UpdateProjectOptionUseCase.Command) {
        updateProjectFactorUseCase.update(
            ProjectFactor(
                projectId = command.projectId,
                unitCost = command.unitCost,
                teamSize = command.teamSize,
                cocomoType = command.cocomoType,
            )
        )

        if (command.projectLanguageList?.isNotEmpty() == true) {
            updateProjectLanguageUseCase.updateAll(
                command.projectId,
                command.projectLanguageList
            )
        }
    }
}