package org.scaleadvisor.backend.global.gemini.data

data class GeminiRequest(
    val prompt: String,
    val temperature: Double? = null,
    val maxOutputTokens: Int? = null
)