package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.api.request.CreateFpWeightsRequest
import org.scaleadvisor.backend.project.api.response.CreateFpWeightsResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "fpweights", description = "fpweights API")
@RequestMapping("projects", produces = ["application/json;charset=utf-8"])
interface FpWeightsAPI {

    @PostMapping("/{projectId}/fp-weights")
    @ResponseStatus(HttpStatus.OK)
    fun create(@PathVariable projectId: Long,
               @RequestBody request: CreateFpWeightsRequest
    ): SuccessResponse<CreateFpWeightsResponse>
}