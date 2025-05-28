package org.scaleadvisor.backend.global.gemini.data

import com.fasterxml.jackson.annotation.JsonProperty

data class UnitProcessResponse(
    @JsonProperty("단위프로세스")
    val unitProcess: String,

    @JsonProperty("요구사항 ID 리스트")
    val ids: List<String>
)
