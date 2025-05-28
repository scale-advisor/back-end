package org.scaleadvisor.backend.global.email.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.global.email.dto.AcceptInvitationResponse
import org.scaleadvisor.backend.global.email.dto.InvitationMailRequest
import org.scaleadvisor.backend.global.email.service.EmailService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Mail Invitation", description = "프로젝트 초대 이메일 발송 및 승인 API")
@RestController
class InvitationMailController(
    private val emailService: EmailService
) {

    @PostMapping("/projects/{projectId}/invitation/request")
    fun invitationEmail(@PathVariable projectId: Long,
                        @RequestBody request: InvitationMailRequest
    ): ResponseEntity<String> {
        emailService.sendInvitaionEmail(request, projectId)
        return ResponseEntity.ok().body("프로젝트 초대 이메일을 전송했습니다.")
    }

    @PostMapping("/invitation/accept")
    fun acceptInvitation(@RequestParam projectId: Long,
                         @RequestParam email: String,
                         @RequestParam token: String
    ): ResponseEntity<AcceptInvitationResponse> {
        val response = emailService.acceptInvitation(projectId, email, token)
        return ResponseEntity.ok().body(response)
    }
}