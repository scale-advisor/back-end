package org.scaleadvisor.backend.project.application.service.unitprocess

import org.scaleadvisor.backend.project.application.port.repository.unitprocess.UpdateUnitProcessPort
import org.scaleadvisor.backend.project.application.port.repository.unitprocess.ValidateUnitProcessPort
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.GetUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.ValidateUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.UnitProcessId
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class ValidateUnitProcessService(
    private val getUnitProcessUseCase: GetUnitProcessUseCase,
    private val validateUnitProcessPort: ValidateUnitProcessPort,
    private val updateUnitProcessPort: UpdateUnitProcessPort,
    @Value("\${validation.prompt.resource}")
    private val promptResource: Resource
): ValidateUnitProcessUseCase {
    private val promptTemplate: String = promptResource.inputStream
        .bufferedReader()
        .use { it.readText() }

    private fun buildValidationLines(projectVersion: ProjectVersion): List<String> {
        val unitProcessList = getUnitProcessUseCase.findAll(projectVersion)


        return unitProcessList.map { record ->
            "- ${record.id} - ${record.name}"
        }
    }

    override fun execute(projectVersion: ProjectVersion) {
        val validationLines = buildValidationLines(projectVersion)
        val fullPrompt = buildString {
            validationLines.forEach { appendLine(it) }
            appendLine()
            appendLine(promptTemplate)
        }
        val rawResponse = validateUnitProcessPort(fullPrompt)

        val ids = rawResponse
            .split("||")
            .map { it.trim() }
            .map { part -> part.substringBefore(" -") }
            .map { UnitProcessId.from(it) }
            .distinct()

        if (ids.isNotEmpty()) {
            updateUnitProcessPort.updateAllIsAmbiguous(ids)
        }
    }

}