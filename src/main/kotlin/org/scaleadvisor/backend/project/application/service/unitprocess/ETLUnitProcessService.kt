package org.scaleadvisor.backend.project.application.service.unitprocess

import org.scaleadvisor.backend.project.application.port.repository.unitprocess.ExtractUnitProcessPort
import org.scaleadvisor.backend.project.application.port.usecase.requirement.GetRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirementunitprocess.CreateRequirementUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.CreateUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.DeleteUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.ETLUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.Requirement
import org.scaleadvisor.backend.project.domain.RequirementUnitProcess
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.scaleadvisor.backend.project.domain.enum.FunctionType
import org.scaleadvisor.backend.project.domain.id.UnitProcessId
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.locks.LockSupport

data class RequirementUnitProcessDTO(
    val requirementNumber: String,
    val unitProcessName: String,
)

@Service
@Transactional
private class ETLUnitProcessService(
    @Value("\${gemini.prompt.unit-process}")
    private val promptResource: Resource,
    private val getRequirementUseCase: GetRequirementUseCase,
    private val deleteUnitProcessUseCase: DeleteUnitProcessUseCase,
    private val createUnitProcessUseCase: CreateUnitProcessUseCase,
    private val createRequirementUnitProcessUseCase: CreateRequirementUnitProcessUseCase,
    private val extractUnitProcess: ExtractUnitProcessPort,
): ETLUnitProcessUseCase {

    private val promptTemplate: String = promptResource.inputStream
        .bufferedReader()
        .use { it.readText() }

    private fun buildRawLines(requirementList: List<Requirement>): List<String> =
        requirementList.map { requirement ->
            listOf(
                requirement.type,
                requirement.number,
                requirement.name,
                requirement.definition,
                requirement.detail,
            ).joinToString(separator = " ") { it.trim()}
        }

    private fun generateDecompositionsBatch(requirementRawList: List<String>): List<String> {
        val allRaw = requirementRawList.joinToString("\n") { "- $it" }

        val fullPrompt = buildString {
            appendLine("다음 요구사항에 대해 단위 프로세스를 분해해 주세요:")
            appendLine(allRaw)
            appendLine()
            appendLine(promptTemplate)
        }

        val aiResponse = extractUnitProcess(fullPrompt)

        return aiResponse
            .split("\n")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }

    private fun getUnitProcessResponsesBatch(result: List<String>): List<RequirementUnitProcessDTO> {
        return result
            .flatMap { line ->
                line.split("||")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
            }
            .mapNotNull { item ->
                val parts = item.split(" - ").map { it.trim() }
                if (parts.size == 2) parts[0] to parts[1] else null
            }
            .associate {
                it.first to it.second.split(",").map(String::trim)
            }
            .flatMap { (unitProcess, requirementNumberList) ->
                requirementNumberList.map { requirementNumber ->
                    RequirementUnitProcessDTO(
                        requirementNumber,
                        unitProcess,
                    )
                }
            }
    }

    fun execute(projectVersion: ProjectVersion) {
        deleteUnitProcessUseCase.deleteAll(projectVersion)

        val requirementList = getRequirementUseCase.findAllFunctionRequirement(projectVersion)
        val requirementRawList = buildRawLines(requirementList)
        val result = generateDecompositionsBatch(requirementRawList)
        val requirementUnitProcessDTOList: List<RequirementUnitProcessDTO> =
            getUnitProcessResponsesBatch(result)

        val requirementMap = requirementList.associate { rec ->
            rec.number to rec.id
        }

        val unitProcessList: MutableList<UnitProcess> = mutableListOf()
        val requirementUnitProcessList: MutableList<RequirementUnitProcess> = mutableListOf()
        requirementUnitProcessDTOList.forEach { requirementUnitProcessDTO ->
            val unitProcess = UnitProcess(
                UnitProcessId.newId(),
                projectVersion.id,
                requirementUnitProcessDTO.unitProcessName,
                FunctionType.UNDEFINED,
                false,
            )
            unitProcessList.add(unitProcess)

            val requirementUnitProcess = RequirementUnitProcess(
                requirementId = requirementMap.getValue(requirementUnitProcessDTO.requirementNumber),
                unitProcessId = unitProcess.id
            )
            requirementUnitProcessList.add(requirementUnitProcess)
            LockSupport.parkNanos(10_000)
        }

        createUnitProcessUseCase.createAll(unitProcessList)
        createRequirementUnitProcessUseCase.createAll(requirementUnitProcessList)
    }

    override fun invoke(projectVersion: ProjectVersion) {
        this.execute(projectVersion)
    }

}