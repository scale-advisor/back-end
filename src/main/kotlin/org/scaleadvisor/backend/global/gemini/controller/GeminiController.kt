package org.scaleadvisor.backend.global.gemini.controller

import org.scaleadvisor.backend.global.gemini.data.UnitProcessResponse
import org.scaleadvisor.backend.global.gemini.service.RequirementPromptService
import org.scaleadvisor.backend.global.gemini.service.UnitProcessValidationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class GeminiController(
    private val requirementPromptService: RequirementPromptService,
    private val validationService: UnitProcessValidationService
) {
    @GetMapping("/{projectId}/unitprocess-generate")
    fun fetchUnitProcesses(@PathVariable projectId: Long): List<UnitProcessResponse> {
        return requirementPromptService.processAndSaveUnitProcesses(projectId)
    }

    @GetMapping("/{projectId}/validate")
    fun validate(@PathVariable projectId: Long): ResponseEntity<List<String>> {
        val validation = validationService.validateAndMark(projectId)
        return ResponseEntity.ok(validation)
    }
}