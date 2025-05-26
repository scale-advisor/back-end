package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.FpWeightsAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.usecase.fpweights.CreateFpWeightsUseCase
import org.scaleadvisor.backend.project.application.port.usecase.fpweights.DeleteFpWeightsUseCase
import org.scaleadvisor.backend.project.application.port.usecase.fpweights.GetFpWeightsUseCase
import org.scaleadvisor.backend.project.application.port.usecase.fpweights.UpdateFpWeightsUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.GetProjectUseCase
import org.scaleadvisor.backend.project.controller.request.fpweights.CreateFpWeightsRequest
import org.scaleadvisor.backend.project.controller.request.fpweights.UpdateFpWeightsRequest
import org.scaleadvisor.backend.project.controller.response.fpweights.CreateFpWeightsResponse
import org.scaleadvisor.backend.project.controller.response.fpweights.FindFpWeightsResponse
import org.scaleadvisor.backend.project.controller.response.fpweights.UpdateFpWeightsResponse
import org.scaleadvisor.backend.project.domain.FpWeights
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class FpWeightsController(
    private val createFpWeightsUseCase: CreateFpWeightsUseCase,
    private val getProjectUseCase: GetProjectUseCase,
    private val getFpWeightsUseCase: GetFpWeightsUseCase,
    private val updateFpWeightsUseCase: UpdateFpWeightsUseCase,
    private val deleteFpWeightsUseCase: DeleteFpWeightsUseCase
): FpWeightsAPI {
    override fun create(projectId: Long,
                        request: CreateFpWeightsRequest
    ): SuccessResponse<CreateFpWeightsResponse> {

        val fpWeights = createFpWeightsUseCase.create(
            CreateFpWeightsUseCase.CreateFpWeightsCommand(
                projectId = ProjectId(projectId),
                ilfWeight = request.ilfWeight,
                eifWeight = request.eifWeight,
                eiWeight  = request.eiWeight,
                eoWeight  = request.eoWeight,
                eqWeight  = request.eqWeight
            )
        )

        return SuccessResponse.from(
            CreateFpWeightsResponse.from(fpWeights)
        )
    }

    override fun find(projectId: Long): SuccessResponse<FindFpWeightsResponse> {
        val projectId = ProjectId.from(projectId)
        val fpWeights = getFpWeightsUseCase.find(projectId)
            ?: FpWeights.getGovStandard(projectId)

        return SuccessResponse.from(FindFpWeightsResponse.from(fpWeights))
    }

    override fun update(
        projectId: Long,
        request: UpdateFpWeightsRequest
    ): SuccessResponse<UpdateFpWeightsResponse> {

        val updated = updateFpWeightsUseCase.update(
            UpdateFpWeightsUseCase.UpdateFpWeightsCommand(
                projectId = ProjectId.from(projectId),
                ilfWeight = request.ilfWeight,
                eifWeight = request.eifWeight,
                eiWeight  = request.eiWeight,
                eoWeight  = request.eoWeight,
                eqWeight  = request.eqWeight
            )
        )

        return SuccessResponse.from(
            UpdateFpWeightsResponse.from(updated)
        )
    }

    override fun delete(projectId: Long) {
        getProjectUseCase
            .find(ProjectId.from(projectId))
            ?: throw NotFoundException("프로젝트를 찾을 수 없습니다. (id=$projectId)")

        deleteFpWeightsUseCase.delete(ProjectId.from(projectId))
    }
}