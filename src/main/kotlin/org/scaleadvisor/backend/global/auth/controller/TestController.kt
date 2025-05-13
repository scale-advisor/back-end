package org.scaleadvisor.backend.global.auth.controller

import org.scaleadvisor.backend.global.security.CustomUserDetails
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Security 및 JWT 임시 테스트 컨트롤러
 */
@RestController
@RequestMapping("/test")
class TestController {
    
    @GetMapping("/secure")
    fun secure(@AuthenticationPrincipal principal: CustomUserDetails): ResponseEntity<Map<String, Any?>> {
        return ResponseEntity.ok(mapOf(
            "userId" to principal.getUserEntity().userId,
            "email" to principal.username
        ))
    }

    @GetMapping("/public")
    fun publicEndpoint(): ResponseEntity<String> =
        ResponseEntity.ok("This is a public endpoint.")
}
