package org.scaleadvisor.backend.project.infrastructure.ai.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

data class FPClassifierClientRequest(
    val unitProcess: String,
    val unitProcessId: String,
)

data class FPClassifierClientResponse(
    val unitProcessId: String,
    val unitProcess: String,
    val functionType: String
)

@FeignClient(
    name = "fp-classifier-request",
    url = "\${fp-classifier.url}"
)
interface FPClassifierClient {
    @PostMapping("\${fp-classifier.path}")
    fun classify(
        @RequestBody requests: List<FPClassifierClientRequest>
    ): List<FPClassifierClientResponse>
}