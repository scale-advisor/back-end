package org.scaleadvisor.backend.project.controller

import org.scaleadvisor.backend.api.AdjustmentFactorAPI
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.controller.response.adjustmentfactor.GetAdjustmentFactorResponse
import org.springframework.web.bind.annotation.RestController

@RestController
private class AdjustmentFactorController : AdjustmentFactorAPI {

    override fun get(): SuccessResponse<GetAdjustmentFactorResponse> {
        return SuccessResponse.from(GetAdjustmentFactorResponse())
    }

}