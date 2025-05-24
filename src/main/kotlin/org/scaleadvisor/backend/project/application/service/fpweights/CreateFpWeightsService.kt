package org.scaleadvisor.backend.project.application.service.fpweights

import org.scaleadvisor.backend.project.application.port.repository.fpweights.CreateFpWeightsPort
import org.scaleadvisor.backend.project.application.port.usecase.fpweights.CreateFpWeightsUseCase
import org.scaleadvisor.backend.project.domain.FpWeights
import org.scaleadvisor.backend.project.domain.id.FpWeightsId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
private class CreateFpWeightsService(
    private val createFpWeightsPort: CreateFpWeightsPort
): CreateFpWeightsUseCase {

    override fun create(command: CreateFpWeightsUseCase.CreateFpWeightsCommand): FpWeights {
        val newId = FpWeightsId.newId()
        val now = LocalDateTime.now()

        val fpWeights = FpWeights(
            fpWeightsId = newId,
            projectId = command.projectId,
            ilfWeight = command.ilfWeight,
            eifWeight = command.eifWeight,
            eiWeight = command.eiWeight,
            eoWeight = command.eoWeight,
            eqWeight = command.eqWeight,
            createdAt = now,
            updatedAt = now
        )

        createFpWeightsPort.create(fpWeights)

        return fpWeights
    }

}