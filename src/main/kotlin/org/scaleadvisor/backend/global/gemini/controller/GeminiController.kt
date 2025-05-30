package org.scaleadvisor.backend.global.gemini.controller

import org.scaleadvisor.backend.global.gemini.data.UnitProcessResponse
import org.scaleadvisor.backend.global.gemini.service.RequirementPromptService
import org.scaleadvisor.backend.global.gemini.service.AdjustmentFactorPromptService
import org.scaleadvisor.backend.global.gemini.service.UnitProcessValidationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class GeminiController(
    private val requirementPromptService: RequirementPromptService,
    private val validationService: UnitProcessValidationService,
    private val adjustmentFactorPromptService: AdjustmentFactorPromptService
) {
    @GetMapping("/{projectId}/unitprocess-generate")
    fun generateUnitProcess(@PathVariable projectId: Long): List<UnitProcessResponse> {
        return requirementPromptService.processAndSaveUnitProcesses(projectId)
    }

    @GetMapping("/{projectId}/validate")
    fun validate(@PathVariable projectId: Long): ResponseEntity<List<String>> {
        val validation = validationService.validateAndMark(projectId)
        return ResponseEntity.ok(validation)
    }

    @GetMapping("/{projectId}/adjustment-generate")
    fun generateAdjustmentFactor(
        @PathVariable projectId: Long
    ) {
        adjustmentFactorPromptService.generateAndSaveAll(projectId).toString()
    }
}