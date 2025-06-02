package org.scaleadvisor.backend.project.infrastructure.ai

data class Content(val parts: List<Part>)
data class Part(val text: String)

data class GenerateResponse(val candidates: List<Candidate>?)
data class Candidate(val content: ContentResponse?)
data class ContentResponse(val parts: List<PartResponse>?)
data class PartResponse(val text: String?)
