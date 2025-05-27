package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.ProjectAdjustmentFactorAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor.GetAdjustmentFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor.UpdateAdjustmentFactorUseCase
import org.scaleadvisor.backend.project.application.service.adjustmentfactor.dto.UpdateAdjustmentFactorDTO
import org.scaleadvisor.backend.project.controller.request.adjustmentfactor.UpdateAdjustmentFactorRequest
import org.scaleadvisor.backend.project.controller.response.adjustmentfactor.GetAdjustmentFactorResponse
import org.scaleadvisor.backend.project.domain.AdjustmentFactor
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.AdjustmentFactorId
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class ProjectAdjustmentFactorController(
    private val getAdjustmentFactorUseCase: GetAdjustmentFactorUseCase,
    private val updateAdjustmentFactorUseCase: UpdateAdjustmentFactorUseCase,
) : ProjectAdjustmentFactorAPI {

    override fun findAll(
        projectId: Long,
        versionNumber: String
    ): SuccessResponse<GetAdjustmentFactorResponse> {
        val adjustmentFactorList: List<AdjustmentFactor> = getAdjustmentFactorUseCase.findAll(
            ProjectVersion.of(
                ProjectId.from(projectId),
                versionNumber
            )
        )

        return SuccessResponse.from(
            GetAdjustmentFactorResponse.from(adjustmentFactorList)
        )
    }


    override fun updateAll(
        projectId: Long,
        request: UpdateAdjustmentFactorRequest
    ) {
        updateAdjustmentFactorUseCase.updateAll(
            request.adjustmentFactorList.map {
                UpdateAdjustmentFactorDTO(
                    id = AdjustmentFactorId.from(it.adjustmentFactorId),
                    level = it.level
                )
            }
        )
    }

}