package org.scaleadvisor.backend.global.gemini.service

import org.scaleadvisor.backend.global.gemini.component.GeminiClient
import org.scaleadvisor.backend.global.gemini.data.UnitProcessResponse
import org.scaleadvisor.backend.global.gemini.repository.GeminiJooqRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RequirementPromptService(
    private val geminiClient: GeminiClient,
    private val geminiJooqRepository: GeminiJooqRepository,
    @Value("\${unit-process.prompt.resource}")
    private val promptResource: Resource
) {
    private val promptTemplate: String = promptResource.inputStream
        .bufferedReader()
        .use { it.readText()}

    private fun buildRawLines(projectId: Long): List<String> =
        geminiJooqRepository.findAllByProject(projectId).map { rec ->
            listOf(
                rec.requirementType,
                rec.requirementNumber,
                rec.requirementName,
                rec.requirementDefinition.orEmpty(),
                rec.requirementDetailNumber.orEmpty(),
                rec.requirementDetail.orEmpty(),
                rec.note.orEmpty()
            ).joinToString(" ") { it.trim() }
        }

    private fun generateDecompositionsBatch(projectId: Long): List<String> {
        val allRaw = buildRawLines(projectId).joinToString("\n") { "- $it" }

        val fullPrompt = buildString {
            appendLine("다음 요구사항에 대해 단위 프로세스를 분해해 주세요:")
            appendLine(allRaw)
            appendLine()
            appendLine(promptTemplate)
        }

        val aiResponse = geminiClient.generate(fullPrompt)

        return aiResponse
            .split("\n")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }

    private fun getUnitProcessResponsesBatch(projectId: Long): List<UnitProcessResponse> {
        return generateDecompositionsBatch(projectId)
            .flatMap { line ->
                line.split("||")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
            }
            .mapNotNull { item ->
                val parts = item.split(" - ").map { it.trim() }
                if (parts.size == 2) parts[0] to parts[1] else null
            }
            .groupBy(
                keySelector = { it.first },
                valueTransform = { it.second }
            )
            .map { (unitProcess, detailIds) ->
                UnitProcessResponse(
                    unitProcess = unitProcess,
                    detailIds   = detailIds
                )
            }
    }

    fun processAndSaveUnitProcesses(projectId: Long): List<UnitProcessResponse> {
        val dto = getUnitProcessResponsesBatch(projectId)

        val reqMap: Map<String, Long> = geminiJooqRepository.findAllByProject(projectId)
            .associate { rec ->
                rec.requirementDetailNumber.orEmpty() to rec.requirementId
            }

        geminiJooqRepository.saveUnitProcess(dto, reqMap)

        return dto
    }
}
