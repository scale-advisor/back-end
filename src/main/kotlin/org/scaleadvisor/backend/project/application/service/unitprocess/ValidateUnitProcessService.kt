package org.scaleadvisor.backend.project.application.service.unitprocess

import org.scaleadvisor.backend.project.application.port.repository.unitprocess.ValidateUnitProcessPort
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.GetUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.UpdateUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.ValidateUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.UnitProcessId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class ValidateUnitProcessService(
    private val getUnitProcessUseCase: GetUnitProcessUseCase,
    private val validateUnitProcessPort: ValidateUnitProcessPort,
    private val updateUnitProcessUseCase: UpdateUnitProcessUseCase,
    @Value("\${gemini.prompt.validation}")
    private val promptResource: Resource,
): ValidateUnitProcessUseCase {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ValidateUnitProcessService::class.java)
    }

    private val promptTemplate: String = promptResource.inputStream
        .bufferedReader()
        .use { it.readText() }

    private fun buildValidationLines(projectVersion: ProjectVersion): List<String> {
        val unitProcessList = getUnitProcessUseCase.findAll(projectVersion)


        return unitProcessList.map { record ->
            "- ${record.id} - ${record.name}"
        }
    }

    fun execute(projectVersion: ProjectVersion) {
        val validationLines = buildValidationLines(projectVersion)
        val fullPrompt = buildString {
            validationLines.forEach { appendLine(it) }
            appendLine()
            appendLine(promptTemplate)
        }
        val rawResponse = validateUnitProcessPort(fullPrompt)

        log.debug("=====================ValidateUnitProcessService:rawResponse=====================")
        log.debug(rawResponse)


        if (rawResponse.isEmpty()) return

        val unitProcessIdList = rawResponse
            .split("||")
            .map { it.trim() }
            .map { part -> part.substringBefore(" -") }
            .map { UnitProcessId.from(it) }
            .distinct()

        if (unitProcessIdList.isNotEmpty()) {
            updateUnitProcessUseCase.updateAllIsAmbiguous(unitProcessIdList)
        }
    }

    override fun invoke(projectVersion: ProjectVersion) {
        this.execute(projectVersion)
    }

}