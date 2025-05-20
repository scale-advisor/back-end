package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.api.request.CreateCocomoScaleFactorRequest
import org.scaleadvisor.backend.project.api.response.CreateCocomoScaleFactorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Tag(name = "cocomo", description = "cocomo API")
@RequestMapping("projects", produces = ["application/json;charset=utf-8"])
interface CocomoApi {

    @PostMapping("/{projectId}/cocomo-scale-factor")
    @ResponseStatus(HttpStatus.OK)
    fun createCocomoScaleFactor(@PathVariable projectId: Long,
                                @RequestBody request: CreateCocomoScaleFactorRequest
    ): SuccessResponse<CreateCocomoScaleFactorResponse>
}