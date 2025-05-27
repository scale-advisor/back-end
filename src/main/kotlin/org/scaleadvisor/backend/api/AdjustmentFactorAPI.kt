package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.controller.response.adjustmentfactor.GetAdjustmentFactorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Tag(name = "Adjustment Factor", description = "보정계수 정보 조회 API")
@RequestMapping("adjustment-factors", produces = ["application/json;charset=utf-8"])
interface AdjustmentFactorAPI {

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun get(): SuccessResponse<GetAdjustmentFactorResponse>

}