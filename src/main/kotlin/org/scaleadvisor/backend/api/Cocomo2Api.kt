package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.controller.request.cocomomultiplier.CreateCocomoMultiplierRequest
import org.scaleadvisor.backend.project.controller.request.cocomoscalefactor.CreateCocomoScaleFactorRequest
import org.scaleadvisor.backend.project.controller.request.cocomomultiplier.UpdateCocomoMultiplierRequest
import org.scaleadvisor.backend.project.controller.request.cocomoscalefactor.UpdateCocomoScaleFactorRequest
import org.scaleadvisor.backend.project.controller.response.*
import org.scaleadvisor.backend.project.controller.response.cocomomultiplier.CreateCocomoMultiplierResponse
import org.scaleadvisor.backend.project.controller.response.cocomomultiplier.FindCocomoMultiplierResponse
import org.scaleadvisor.backend.project.controller.response.cocomomultiplier.UpdateCocomoMultiplierResponse
import org.scaleadvisor.backend.project.controller.response.cocomoscalefactor.CreateCocomoScaleFactorResponse
import org.scaleadvisor.backend.project.controller.response.cocomoscalefactor.FindCocomoScaleFactorResponse
import org.scaleadvisor.backend.project.controller.response.cocomoscalefactor.UpdateCocomoScaleFactorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Tag(name = "cocomo2", description = "cocomo2 API")
@RequestMapping("projects", produces = ["application/json;charset=utf-8"])
interface Cocomo2Api {

    @PostMapping("/{projectId}/cocomo-scale-factor")
    @ResponseStatus(HttpStatus.OK)
    fun createCocomoScaleFactor(@PathVariable projectId: Long,
                                @RequestBody request: CreateCocomoScaleFactorRequest
    ): SuccessResponse<CreateCocomoScaleFactorResponse>

    @GetMapping("/{projectId}/cocomo-scale-factor")
    @ResponseStatus(HttpStatus.OK)
    fun findCocomoScaleFactor(@PathVariable projectId: Long
    ): SuccessResponse<FindCocomoScaleFactorResponse>

    @PutMapping("/{projectId}/cocomo-scale-factor")
    @ResponseStatus(HttpStatus.OK)
    fun updateCocomoScaleFactor(@PathVariable projectId: Long,
                                @RequestBody request: UpdateCocomoScaleFactorRequest
    ): SuccessResponse<UpdateCocomoScaleFactorResponse>

    @DeleteMapping("/{projectId}/cocomo-scale-factor")
    @ResponseStatus(HttpStatus.OK)
    fun deleteCocomoScaleFactor(@PathVariable projectId: Long)

    @PostMapping("/{projectId}/cocomo-multiplier")
    @ResponseStatus(HttpStatus.OK)
    fun createCocomoMultiplier(@PathVariable projectId: Long,
                               @RequestBody request: CreateCocomoMultiplierRequest
    ): SuccessResponse<CreateCocomoMultiplierResponse>

    @GetMapping("/{projectId}/cocomo-multiplier")
    @ResponseStatus(HttpStatus.OK)
    fun findCocomoMultiplier(@PathVariable projectId: Long
    ): SuccessResponse<FindCocomoMultiplierResponse>

    @PutMapping("/{projectId}/cocomo-multiplier")
    @ResponseStatus(HttpStatus.OK)
    fun updateCocomoMultiplier(@PathVariable projectId: Long,
                               @RequestBody request: UpdateCocomoMultiplierRequest
    ): SuccessResponse<UpdateCocomoMultiplierResponse>

    @DeleteMapping("/{projectId}/cocomo-multiplier")
    @ResponseStatus(HttpStatus.OK)
    fun deleteCocomoMultiplier(@PathVariable projectId: Long)
}