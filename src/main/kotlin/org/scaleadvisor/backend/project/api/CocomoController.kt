package org.scaleadvisor.backend.project.api

import org.scaleadvisor.backend.api.Cocomo2Api
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.api.request.CreateCocomoMultiplierRequest
import org.scaleadvisor.backend.project.api.request.CreateCocomoScaleFactorRequest
import org.scaleadvisor.backend.project.api.request.UpdateCocomoMultiplierRequest
import org.scaleadvisor.backend.project.api.request.UpdateCocomoScaleFactorRequest
import org.scaleadvisor.backend.project.api.response.*
import org.scaleadvisor.backend.project.application.port.usecase.cocomomultiplier.CreateCocomoMultiplierUseCase
import org.scaleadvisor.backend.project.application.port.usecase.cocomomultiplier.FindCocomoMultiplierUseCase
import org.scaleadvisor.backend.project.application.port.usecase.cocomomultiplier.UpdateCocomoMultiplierUseCase
import org.scaleadvisor.backend.project.application.port.usecase.cocomoscalefactor.CreateCocomoScaleFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.cocomoscalefactor.DeleteCocomoScaleFactorUseCase
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
    private val deleteCocomoScaleFactorUseCase: DeleteCocomoScaleFactorUseCase,

    private val createCocomoMultiplierUseCase: CreateCocomoMultiplierUseCase,
    private val findCocomoMultiplierUseCase: FindCocomoMultiplierUseCase,
    private val updateCocomoMultiplierUseCase: UpdateCocomoMultiplierUseCase,

    private val getProjectUseCase: GetProjectUseCase
): Cocomo2Api {

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

    override fun deleteCocomoScaleFactor(projectId: Long) {
        getProjectUseCase.find(ProjectId.of(projectId))
            ?:throw NotFoundException("프로젝트를 찾을 수 없습니다. (id=$projectId)")

        deleteCocomoScaleFactorUseCase.delete(ProjectId.of(projectId))
    }

    override fun createCocomoMultiplier(
        projectId: Long,
        request: CreateCocomoMultiplierRequest
    ): SuccessResponse<CreateCocomoMultiplierResponse> {
        val command = CreateCocomoMultiplierUseCase.CreateCocomoMultiplierCommand(
            projectId = ProjectId(projectId),
            rcpx = request.rcpx,
            ruse = request.ruse,
            pdif = request.pdif,
            pers = request.pers,
            sced = request.sced,
            fcil = request.fcil
        )

        val created = createCocomoMultiplierUseCase.create(command)

        return SuccessResponse.from(
            CreateCocomoMultiplierResponse.from(created)
        )
    }

    override fun findCocomoMultiplier(projectId: Long): SuccessResponse<FindCocomoMultiplierResponse> {
        val cocomoMultiplier = findCocomoMultiplierUseCase.find(ProjectId(projectId))
            ?: throw NotFoundException("프로젝트를 찾을 수 없습니다. (id=$projectId)")

        return SuccessResponse.from(
            FindCocomoMultiplierResponse.from(cocomoMultiplier)
        )
    }

    override fun updateCocomoMultiplier(
        projectId: Long,
        request: UpdateCocomoMultiplierRequest
    ): SuccessResponse<UpdateCocomoMultiplierResponse> {
        getProjectUseCase.find(ProjectId.of(projectId))
            ?: throw NotFoundException("프로젝트를 찾을 수 없습니다. (id=$projectId)")

        val updated = updateCocomoMultiplierUseCase.update(
            UpdateCocomoMultiplierUseCase.UpdateCocomoMultiplierCommand(
                projectId = ProjectId.of(projectId),
                rcpx = request.rcpx,
                ruse = request.ruse,
                pdif = request.pdif,
                pers = request.pers,
                sced = request.sced,
                fcil = request.fcil
            )
        )

        return SuccessResponse.from(
            UpdateCocomoMultiplierResponse.from(updated)
        )
    }
}