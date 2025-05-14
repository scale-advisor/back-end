package org.scaleadvisor.backend.global.email.controller

import jakarta.servlet.http.HttpServletResponse
import org.scaleadvisor.backend.global.email.dto.PwdResetConfirmRequest
import org.scaleadvisor.backend.global.email.dto.PwdResetRequest
import org.scaleadvisor.backend.global.email.service.EmailService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView

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

    @GetMapping("/password-reset")
    fun verifyResetToken(@RequestParam token: String, response: HttpServletResponse
    ): RedirectView {
        val redirectUrl = emailService.confirmResetToken(token)!!
        return RedirectView(redirectUrl)
    }

    @PostMapping("/password-reset")
    fun resetPassword(@RequestBody request: PwdResetConfirmRequest
    ): ResponseEntity<String> {
        emailService.resetPassword(request)
        return ResponseEntity.ok().body("비밀번호가 성공적으로 변경되었습니다.")
    }
}