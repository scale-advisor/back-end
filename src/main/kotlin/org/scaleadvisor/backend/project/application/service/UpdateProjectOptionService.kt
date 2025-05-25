package org.scaleadvisor.backend.project.application.service

import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.project.application.port.usecase.UpdateProjectOptionUseCase
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsEditorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectfactor.UpdateProjectFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectlanguage.UpdateProjectLanguageUseCase
import org.scaleadvisor.backend.project.domain.ProjectFactor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class UpdateProjectOptionService(
    private val checkIsEditorUseCase: CheckIsEditorUseCase,
    private val updateProjectFactorUseCase: UpdateProjectFactorUseCase,
    private val updateProjectLanguageUseCase: UpdateProjectLanguageUseCase,
) : UpdateProjectOptionUseCase {
    override fun update(command: UpdateProjectOptionUseCase.Command) {

        if (!checkIsEditorUseCase.checkIsEditor(
                command.projectId.toLong())) {
            throw ForbiddenException("프로젝트 옵션을 수정할 권한이 없습니다.")
        }

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