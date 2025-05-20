package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.api.request.CreateFpWeightsRequest
import org.scaleadvisor.backend.project.api.request.UpdateFpWeightsRequest
import org.scaleadvisor.backend.project.api.response.CreateFpWeightsResponse
import org.scaleadvisor.backend.project.api.response.FindFpWeightsResponse
import org.scaleadvisor.backend.project.api.response.UpdateFpWeightsResponse
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

    @GetMapping("/{projectId}/fp-weights")
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable projectId: Long): SuccessResponse<FindFpWeightsResponse>

    @PutMapping("/{projectId}/fp-weights")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable projectId: Long,
               @RequestBody request: UpdateFpWeightsRequest
    ): SuccessResponse<UpdateFpWeightsResponse>

    @DeleteMapping("/{projectId}/fp-weights")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable projectId: Long)
}