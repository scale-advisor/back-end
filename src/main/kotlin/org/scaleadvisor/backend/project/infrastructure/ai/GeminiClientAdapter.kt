package org.scaleadvisor.backend.project.infrastructure.ai

//import org.scaleadvisor.backend.global.gemini.data.GenerateRequest
//import org.scaleadvisor.backend.global.gemini.data.GenerationConfig
import org.scaleadvisor.backend.global.gemini.data.Content
import org.scaleadvisor.backend.global.gemini.data.GenerateResponse
import org.scaleadvisor.backend.global.gemini.data.Part
import org.scaleadvisor.backend.project.application.port.repository.unitprocess.ExtractUnitProcessPort
import org.scaleadvisor.backend.project.application.port.repository.unitprocess.ValidateUnitProcessPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

private data class GenerateRequest(
    val contents: List<Content>,
    val generationConfig: GenerationConfig
)

private data class GenerationConfig(
    val temperature: Double,
    val maxOutputTokens: Int
)

@Component
private class GeminiClientAdapter(
    @Value("\${gemini.api.url}") private val apiUrl: String,
    @Value("\${gemini.api.key}") private val apiKey: String,
    @Value("\${gemini.api.model}") private val model: String,
    private val restTemplate: RestTemplate,
) : ExtractUnitProcessPort, ValidateUnitProcessPort {

    private val TEMPERATURE: Double = 0.0
    private val MAX_OUTPUT_TOKENS: Int = 100000

    override fun invoke(prompt: String): String {
        val requestUrl = UriComponentsBuilder
            .fromHttpUrl(apiUrl)
            .path(model)
            .queryParam("key", apiKey)
            .build()
            .toUriString()

        val payload = GenerateRequest(
            contents = listOf(Content(parts = listOf(Part(text = prompt)))),
            generationConfig = GenerationConfig(TEMPERATURE, MAX_OUTPUT_TOKENS)
        )

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }
        val entity = HttpEntity(payload, headers)

        val responseEntity = restTemplate.postForEntity(requestUrl, entity, GenerateResponse::class.java)
        val responseBody = responseEntity.body

        return responseBody
            ?.candidates
            ?.firstOrNull()
            ?.content
            ?.parts
            ?.firstOrNull()
            ?.text
            .orEmpty()
    }
}