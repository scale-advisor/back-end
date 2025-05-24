package org.scaleadvisor.backend.global.email.controller

import org.scaleadvisor.backend.global.email.dto.AcceptInvitationRequest
import org.scaleadvisor.backend.global.email.dto.InvitationMailRequest
import org.scaleadvisor.backend.global.email.service.EmailService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class InvitationController(
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
    fun acceptInvitation(@RequestBody request: AcceptInvitationRequest
    ): ResponseEntity<String> {
        emailService.acceptInvitation(request)
        return ResponseEntity.ok().body("프로젝트 초대를 승인했습니다.")
    }
}