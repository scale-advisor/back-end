package org.scaleadvisor.backend.global.gemini.service

import org.scaleadvisor.backend.global.gemini.component.GeminiClient
import org.scaleadvisor.backend.global.gemini.repository.GeminiJooqRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UnitProcessValidationService(
    private val geminiClient: GeminiClient,
    private val geminiJooqRepository: GeminiJooqRepository,
    @Value("\${validation.prompt.resource}")
    private val promptResource: Resource
) {
    private val promptTemplate: String = promptResource.inputStream
        .bufferedReader()
        .use { it.readText() }

    private fun buildValidationLines(projectId: Long): List<String> {
        val unitProcesses = geminiJooqRepository.findAllUnitProcessesByProject(projectId)

        return unitProcesses.map { record ->
            "- ${record.unitProcessId} - ${record.unitProcessName}"
        }
    }

    fun validateAndMark(projectId: Long): List<String> {

        val validationLines = buildValidationLines(projectId)
        val fullPrompt = buildString {
            validationLines.forEach { appendLine(it) }
            appendLine()
            appendLine(promptTemplate)
        }
        val rawResponse = geminiClient.generate(fullPrompt)

        val ids = rawResponse
            .split("||")
            .map { it.trim() }
            .map { part -> part.substringBefore(" -") }
            .distinct()

        if (ids.isNotEmpty()) {
            geminiJooqRepository.markAsAmbiguous(ids)
        }

        return ids
    }
}