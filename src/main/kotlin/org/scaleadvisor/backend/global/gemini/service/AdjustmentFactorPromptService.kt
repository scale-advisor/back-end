package org.scaleadvisor.backend.global.gemini.service

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.global.gemini.component.GeminiClient
import org.scaleadvisor.backend.global.gemini.repository.GeminiJooqRepository
import org.scaleadvisor.backend.project.application.port.usecase.version.GetProjectVersionUseCase
import org.scaleadvisor.backend.project.domain.AdjustmentFactor
import org.scaleadvisor.backend.project.domain.enum.AdjustmentFactorType
import org.scaleadvisor.backend.project.domain.enum.RequirementCategoryName
import org.scaleadvisor.backend.project.domain.id.AdjustmentFactorId
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class AdjustmentFactorPromptService(
    private val geminiJooqRepository: GeminiJooqRepository,
    private val geminiClient: GeminiClient,
    private val getProjectVersionUseCase: GetProjectVersionUseCase,
    @Value("\${gemini.prompt.security}")
    private val securityPrompt: Resource,
    @Value("\${gemini.prompt.integration_complexity}")
    private val integrationPrompt: Resource,
    @Value("\${gemini.prompt.operational_compatibility}")
    private val operationalPrompt: Resource,
    @Value("\${gemini.prompt.performance}")
    private val performancePrompt: Resource,
) {
    private val promptTemplates: Map<RequirementCategoryName, String> = mapOf(
        RequirementCategoryName.SECURITY to securityPrompt.readText(),
        RequirementCategoryName.INTEGRATION_COMPLEXITY to integrationPrompt.readText(),
        RequirementCategoryName.OPERATIONAL_COMPATIBILITY to operationalPrompt.readText(),
        RequirementCategoryName.PERFORMANCE to performancePrompt.readText(),
    )

    private fun Resource.readText(): String =
        this.inputStream.bufferedReader().use { it.readText() }

    fun generateAndSaveAll(projectId: Long) {
        val projectVersion = getProjectVersionUseCase.findLatest(ProjectId.from(projectId))
            ?: throw NotFoundException("프로젝트($projectId) 버전을 찾을 수 없습니다.")
        val categories = geminiJooqRepository.findAllCategories(projectId)
            .filter { cat ->
                runCatching { RequirementCategoryName.valueOf(cat.requirementCategoryName) }
                    .getOrNull()
                    ?.let { it in promptTemplates.keys }
                    ?: false
            }


    val factors = categories.map { cat ->
        val name = RequirementCategoryName.valueOf(cat.requirementCategoryName)

        val template = promptTemplates[name]
            ?: throw NotFoundException("보정계수 템플릿이 없습니다: $name")

            val lines = geminiJooqRepository.listRequirementsByPrefix(projectId, cat.requirementCategoryPrefix)

            val prompt = buildString {
                lines.forEach { appendLine("- $it") }
                appendLine()
                appendLine(template)
            }

            val aiResponse: String = geminiClient.generate(prompt)

            val level = Regex("""\d+""")
                .find(aiResponse)
                ?.value
                ?.toIntOrNull()
                ?: throw NotFoundException("$name 응답에서 레벨 숫자를 찾을 수 없습니다:\n$aiResponse")

            AdjustmentFactor(
                id = AdjustmentFactorId.newId(),
                projectVersionId = projectVersion.id,
                type = AdjustmentFactorType.valueOf(name.name),
                level = level
            )
        }

        if (factors.isEmpty()) {
            throw NotFoundException("AI 응답에서 보정계수를 하나도 파싱하지 못했습니다.")
        }

        val count = geminiJooqRepository.saveAdjustmentFactors(projectVersion.id, factors)
        if (count == 0) {
            throw NotFoundException("보정계수 저장에 실패했습니다.")
        }
    }
}