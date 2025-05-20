package org.scaleadvisor.backend.project.api

import org.scaleadvisor.backend.api.FpWeightsAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.scaleadvisor.backend.project.api.request.CreateFpWeightsRequest
import org.scaleadvisor.backend.project.api.request.UpdateFpWeightsRequest
import org.scaleadvisor.backend.project.api.response.CreateFpWeightsResponse
import org.scaleadvisor.backend.project.api.response.FindFpWeightsResponse
import org.scaleadvisor.backend.project.api.response.UpdateFpWeightsResponse
import org.scaleadvisor.backend.project.application.port.usecase.fpweights.CreateFpWeightsUseCase
import org.scaleadvisor.backend.project.application.port.usecase.fpweights.FindFpWeightsUseCase
import org.scaleadvisor.backend.project.application.port.usecase.fpweights.UpdateFpWeightsUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.GetProjectUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class FpWeightsController(
    private val createFpWeightsUseCase: CreateFpWeightsUseCase,
    private val getProjectUseCase: GetProjectUseCase,
    private val findFpWeightsUseCase: FindFpWeightsUseCase,
    private val updateFpWeightsUseCase: UpdateFpWeightsUseCase
): FpWeightsAPI {
    override fun create(projectId: Long,
                        request: CreateFpWeightsRequest
    ): SuccessResponse<CreateFpWeightsResponse> {
        val currentUserId = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw ForbiddenException("현재 인증이 되지 않은 접근 입니다.")

        val project = getProjectUseCase
            .find(ProjectId(projectId))
            ?: throw NotFoundException("프로젝트를 찾을 수 없습니다. (id=$projectId)")

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
        val fpWeights = findFpWeightsUseCase
            .find(ProjectId.of(projectId))
            ?: throw NotFoundException("프로젝트를 찾을 수 없습니다. (id=$projectId)")

        return SuccessResponse.from(FindFpWeightsResponse.from(fpWeights))
    }

    override fun update(
        projectId: Long,
        request: UpdateFpWeightsRequest
    ): SuccessResponse<UpdateFpWeightsResponse> {
        val currentUserId = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw ForbiddenException("현재 인증이 되지 않은 접근 입니다.")

        val project = getProjectUseCase
            .find(ProjectId.of(projectId))
            ?: throw NotFoundException("프로젝트를 찾을 수 없습니다. (id=$projectId)")

        val updated = updateFpWeightsUseCase.update(
            UpdateFpWeightsUseCase.UpdateFpWeightsCommand(
                projectId = ProjectId.of(projectId),
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
}