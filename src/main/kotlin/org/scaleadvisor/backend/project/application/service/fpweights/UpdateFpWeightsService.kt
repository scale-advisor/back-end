package org.scaleadvisor.backend.project.application.service.fpweights

import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.fpweights.UpdateFpWeightsPort
import org.scaleadvisor.backend.project.application.port.usecase.fpweights.FindFpWeightsUseCase
import org.scaleadvisor.backend.project.application.port.usecase.fpweights.UpdateFpWeightsUseCase
import org.scaleadvisor.backend.project.application.port.usecase.member.CheckIsEditorUseCase
import org.scaleadvisor.backend.project.domain.FpWeights
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class UpdateFpWeightsService(
    private val checkIsEditorUseCase: CheckIsEditorUseCase,
    private val findFpWeightsUseCase: FindFpWeightsUseCase,
    private val updateFpWeightsPort: UpdateFpWeightsPort
): UpdateFpWeightsUseCase {

    override fun update(command: UpdateFpWeightsUseCase.UpdateFpWeightsCommand): FpWeights {

        if (!checkIsEditorUseCase.checkIsEditor(command.projectId.toLong())) {
            throw ForbiddenException("프로젝트 요소를 수정할 권한이 없습니다.")
        }

        val fpWeights = findFpWeightsUseCase.find(command.projectId)
            ?: throw NotFoundException("프로젝트를 찾을 수 없습니다. (id=$command.projectId)")

        fpWeights.update(
            ilfWeight = command.ilfWeight,
            eifWeight = command.eifWeight,
            eiWeight  = command.eiWeight,
            eoWeight  = command.eoWeight,
            eqWeight  = command.eqWeight
        )

        updateFpWeightsPort.update(fpWeights)

        return fpWeights
    }
}