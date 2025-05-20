package org.scaleadvisor.backend.project.api

import org.scaleadvisor.backend.api.CocomoApi
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.api.request.CreateCocomoScaleFactorRequest
import org.scaleadvisor.backend.project.api.request.UpdateCocomoScaleFactorRequest
import org.scaleadvisor.backend.project.api.response.CreateCocomoScaleFactorResponse
import org.scaleadvisor.backend.project.api.response.FindCocomoScaleFactorResponse
import org.scaleadvisor.backend.project.api.response.UpdateCocomoScaleFactorResponse
import org.scaleadvisor.backend.project.application.port.usecase.cocomoscalefactor.CreateCocomoScaleFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.cocomoscalefactor.FindCocomoScaleFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.cocomoscalefactor.UpdateCocomoScaleFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.GetProjectUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.web.bind.annotation.RestController

@RestController
private class CocomoController(
    private val createCocomoScaleFactorUseCase: CreateCocomoScaleFactorUseCase,
    private val findCocomoScaleFactorUseCase: FindCocomoScaleFactorUseCase,
    private val updateCocomoScaleFactorUseCase: UpdateCocomoScaleFactorUseCase,
    private val getProjectUseCase: GetProjectUseCase
): CocomoApi {

    override fun createCocomoScaleFactor(
        projectId: Long,
        request: CreateCocomoScaleFactorRequest
    ): SuccessResponse<CreateCocomoScaleFactorResponse> {
        val command = CreateCocomoScaleFactorUseCase.CreateCocomoScaleFactorCommand(
            projectId = ProjectId(projectId),
            prec = request.prec,
            flex = request.flex,
            resl = request.resl,
            team = request.team,
            pmat = request.pmat
        )

        val created = createCocomoScaleFactorUseCase.create(command)

        return SuccessResponse.from(
            CreateCocomoScaleFactorResponse.from(created)
        )
    }

    override fun findCocomoScaleFactor(projectId: Long
    ): SuccessResponse<FindCocomoScaleFactorResponse> {
        val cocomoScaleFactor = findCocomoScaleFactorUseCase
            .find(ProjectId.of(projectId))
            ?: throw NotFoundException("프로젝트를 찾을 수 없습니다. (id=$projectId)")

        return SuccessResponse.from(
            FindCocomoScaleFactorResponse.from(cocomoScaleFactor)
        )
    }

    override fun updateCocomoScaleFactor(
        projectId: Long,
        request: UpdateCocomoScaleFactorRequest
    ): SuccessResponse<UpdateCocomoScaleFactorResponse> {
        getProjectUseCase.find(ProjectId.of(projectId))
            ?: throw NotFoundException("프로젝트를 찾을 수 없습니다. (id=$projectId)")

        val updated = updateCocomoScaleFactorUseCase.update(
            UpdateCocomoScaleFactorUseCase.UpdateCocomoScaleFactorCommand(
                projectId = ProjectId.of(projectId),
                prec = request.prec,
                flex = request.flex,
                resl = request.resl,
                team = request.team,
                pmat = request.pmat
            )
        )

        return SuccessResponse.from(
            UpdateCocomoScaleFactorResponse.from(updated)
        )
    }
}