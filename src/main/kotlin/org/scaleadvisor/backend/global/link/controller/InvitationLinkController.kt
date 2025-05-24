package org.scaleadvisor.backend.global.link.controller

import org.scaleadvisor.backend.global.link.dto.InvitationLinkResponse
import org.scaleadvisor.backend.global.link.service.InvitationLinkService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/projects/{projectId}")
class InvitationLinkController(
    private val invitationLinkService: InvitationLinkService
) {
    @PostMapping("/invitations/link")
    fun generateInvitationLink(
        @PathVariable projectId: Long
    ): ResponseEntity<InvitationLinkResponse> {
        val response = invitationLinkService.generateInvitationLink(projectId)

        return ResponseEntity.ok().body(response)
    }

    @PostMapping("/join")
    fun joinProject(
        @PathVariable projectId: Long,
        @RequestParam("invitationToken") invitationToken: String
    ) {
        invitationLinkService.joinProjectByToken(projectId, invitationToken)
    }
}