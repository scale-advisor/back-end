package org.scaleadvisor.backend.global.gemini.component

import org.scaleadvisor.backend.global.gemini.data.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class GeminiClient(
    @Value("\${gemini.api.url}") private val apiUrl: String,
    @Value("\${gemini.api.key}") private val apiKey: String,
    @Value("\${gemini.api.model}") private val model: String,
    private val restTemplate: RestTemplate
) {
    fun generate(
        prompt: String,
        temperature: Double = 0.8,
        maxOutputTokens: Int = 256
    ): String {
        val requestUrl = UriComponentsBuilder
            .fromHttpUrl(apiUrl)
            .path(model)
            .queryParam("key", apiKey)
            .build()
            .toUriString()

        val payload = GenerateRequest(
            contents = listOf(Content(parts = listOf(Part(text = prompt)))),
            generationConfig = GenerationConfig(temperature, maxOutputTokens)
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