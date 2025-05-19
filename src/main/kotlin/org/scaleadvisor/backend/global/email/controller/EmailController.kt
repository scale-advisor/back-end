package org.scaleadvisor.backend.global.email.controller

import org.scaleadvisor.backend.global.email.dto.ConfirmMailRequest
import org.scaleadvisor.backend.global.email.dto.ConfirmSignupRequest
import org.scaleadvisor.backend.global.email.dto.PwdResetConfirmRequest
import org.scaleadvisor.backend.global.email.dto.PwdResetRequest
import org.scaleadvisor.backend.global.email.service.EmailService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class EmailController(
    private val emailService: EmailService
) {
    @PostMapping("/email-verification/request")
    fun verifyEmail(@RequestBody request: ConfirmMailRequest
    ): ResponseEntity<String> {
        emailService.sendConfirmationEmail(request)
        return ResponseEntity.ok().body("회원가입 승인 이메일을 발송했습니다.")
    }

    @PostMapping("/email-verification")
    fun confirmSignup(@RequestBody request: ConfirmSignupRequest
    ): ResponseEntity<String> {
        emailService.confirmSignup(request)
        return ResponseEntity.ok().body("회원가입 승인이 완료 되었습니다.")
    }

    @PostMapping("/password-reset/request")
    fun resetPasswordEmail(@RequestBody request: PwdResetRequest
    ): ResponseEntity<String> {
        emailService.sendResetPasswordEmail(request)
        return ResponseEntity.ok().body("비밀번호 재설정 이메일을 발송했습니다.")
    }

    @PostMapping("/password-reset")
    fun resetPassword(@RequestBody request: PwdResetConfirmRequest
    ): ResponseEntity<String> {
        emailService.resetPassword(request)
        return ResponseEntity.ok().body("비밀번호가 성공적으로 변경되었습니다.")
    }
}