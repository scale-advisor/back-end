package org.scaleadvisor.backend.global.gemini.data

data class GenerateRequest(
    val contents: List<Content>,
    val generationConfig: GenerationConfig
)

data class Content(val parts: List<Part>)
data class Part(val text: String)
data class GenerationConfig(
    val temperature: Double,
    val maxOutputTokens: Int
)

data class GenerateResponse(val candidates: List<Candidate>?)
data class Candidate(val content: ContentResponse?)
data class ContentResponse(val parts: List<PartResponse>?)
data class PartResponse(val text: String?)
