package org.scaleadvisor.backend.global.link.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.global.link.dto.InvitationLinkResponse
import org.scaleadvisor.backend.global.link.dto.JoinProjectResponse
import org.scaleadvisor.backend.global.link.service.InvitationLinkService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Link Invitation", description = "링크 초대 및 승인 API")
@RestController
@RequestMapping("/projects")
class InvitationLinkController(
    private val invitationLinkService: InvitationLinkService
) {
    @PostMapping("/{projectId}/invitations/link")
    fun generateInvitationLink(
        @PathVariable projectId: Long
    ): ResponseEntity<InvitationLinkResponse> {
        val response = invitationLinkService.generateInvitationLink(projectId)

        return ResponseEntity.ok().body(response)
    }

    @PostMapping("/join")
    fun joinProject(
        @RequestParam("invitationToken") invitationToken: String
    ): ResponseEntity<JoinProjectResponse> {
        val response = invitationLinkService.joinProjectByToken(invitationToken)

        return ResponseEntity.ok().body(response)
    }
}