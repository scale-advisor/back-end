package org.scaleadvisor.backend.project.application.service.adjustmentfactor

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.adjustmentfactor.AnalyzeAdjustmentFactorPort
import org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor.AnalyzeAdjustmentFactorLevelUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirement.GetRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirementcategory.GetRequirementCategoryUseCase
import org.scaleadvisor.backend.project.domain.AdjustmentFactor
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.Requirement
import org.scaleadvisor.backend.project.domain.RequirementCategory
import org.scaleadvisor.backend.project.domain.enum.AdjustmentFactorType
import org.scaleadvisor.backend.project.domain.enum.RequirementCategoryName
import org.scaleadvisor.backend.project.domain.id.AdjustmentFactorId
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class AnalyzeAdjustmentFactorLevelService(
    private val getRequirementUseCase: GetRequirementUseCase,
    private val getRequirementCategoryUseCase: GetRequirementCategoryUseCase,
    private val analyzeAdjustmentFactor: AnalyzeAdjustmentFactorPort,

    @Value("\${gemini.prompt.security}")
    private val securityPrompt: Resource,
    @Value("\${gemini.prompt.integration_complexity}")
    private val integrationPrompt: Resource,
    @Value("\${gemini.prompt.operational_compatibility}")
    private val operationalPrompt: Resource,
    @Value("\${gemini.prompt.performance}")
    private val performancePrompt: Resource,
): AnalyzeAdjustmentFactorLevelUseCase {

    private val promptTemplates: Map<RequirementCategoryName, String> = mapOf(
        RequirementCategoryName.SECURITY to securityPrompt.readText(),
        RequirementCategoryName.INTEGRATION_COMPLEXITY to integrationPrompt.readText(),
        RequirementCategoryName.OPERATIONAL_COMPATIBILITY to operationalPrompt.readText(),
        RequirementCategoryName.PERFORMANCE to performancePrompt.readText(),
    )

    private fun Resource.readText(): String =
        this.inputStream.bufferedReader().use { it.readText() }

    fun execute(projectVersion: ProjectVersion): List<AdjustmentFactor> {
        val categories = getRequirementCategoryUseCase.findAll(projectVersion)
            .filter { cat ->
                runCatching { cat.name }
                    .getOrNull()
                    ?.let { it in promptTemplates.keys }
                    ?: false
            }

        val categoryNameMap: Map<RequirementCategoryName, List<RequirementCategory>> = categories.groupBy { it.name }

        val factors = RequirementCategoryName.entries
            .filter { it != RequirementCategoryName.FUNCTION }
            .map { categoryName ->
            val template = promptTemplates[categoryName]
                ?: throw NotFoundException("보정계수 템플릿이 없습니다: $categoryName.name")

            val requirementList: List<Requirement> = getRequirementUseCase.findAll(
                projectVersion,
                categoryNameMap[categoryName]?.map { it.prefix } ?: emptyList(),
            )
            val lines : List<String> = requirementList.map {
                "${it.number}: ${it.detail}"
            }

            val prompt = buildString {
                lines.forEach { appendLine("- $it") }
                appendLine()
                appendLine(template)
            }

            val aiResponse: String = analyzeAdjustmentFactor(prompt)

            val level = Regex("""\d+""")
                .find(aiResponse)
                ?.value
                ?.toIntOrNull()
                ?: throw NotFoundException("$categoryName.name 응답에서 레벨 숫자를 찾을 수 없습니다:\n$aiResponse")

            AdjustmentFactor(
                id = AdjustmentFactorId.newId(),
                projectVersionId = projectVersion.id,
                type = AdjustmentFactorType.valueOf(categoryName.name),
                level = level
            )
        }

        if (factors.isEmpty()) {
            throw NotFoundException("AI 응답에서 보정계수를 하나도 파싱하지 못했습니다.")
        }

        return factors
    }

    override fun invoke(projectVersion: ProjectVersion): List<AdjustmentFactor> {
        return this.execute(projectVersion)
    }


}