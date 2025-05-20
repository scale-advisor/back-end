package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "FpWeights", description = "FpWeights API")
@RequestMapping("projects", produces = ["application/json;charset=utf-8"])
interface FpWeightsAPI {
}