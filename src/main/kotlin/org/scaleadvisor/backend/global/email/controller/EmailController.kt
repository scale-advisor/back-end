package org.scaleadvisor.backend.global.email.controller

import org.scaleadvisor.backend.global.email.dto.PwdResetRequest
import org.scaleadvisor.backend.global.email.service.EmailService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class EmailController(
    private val emailService: EmailService
) {
    @GetMapping("/email-verification")
    fun verifyEmail(@RequestParam email: String, @RequestParam token: String
    ): ResponseEntity<String> = emailService.confirmSignup(email, token)

    @PostMapping("/password-reset/request")
    fun resetPasswordEmail(@RequestBody request: PwdResetRequest
    ): ResponseEntity<String> = emailService.sendResetPasswordEmail(request)
}