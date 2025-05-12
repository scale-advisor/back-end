package org.scaleadvisor.backend.global.email.controller

import org.scaleadvisor.backend.global.email.service.EmailService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class EmailController(
    private val emailService: EmailService
) {
    @GetMapping("/{userId}/email-verification")
    fun verifyEmail(@PathVariable userId: Long, @RequestParam token: String
    ): ResponseEntity<String> =
        emailService.confirmSignup(userId, token)
}