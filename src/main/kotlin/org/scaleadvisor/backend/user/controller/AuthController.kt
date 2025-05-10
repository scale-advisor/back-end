package org.scaleadvisor.backend.user.controller

import org.scaleadvisor.backend.user.dto.LoginRequest
import org.scaleadvisor.backend.user.service.AuthService
import org.scaleadvisor.backend.user.dto.SignUpRequest
import org.scaleadvisor.backend.user.dto.LoginResponse
import org.scaleadvisor.backend.user.dto.RefreshRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<Long> {
        val id = authService.signup(request)
        return ResponseEntity.ok().body(id)
    }

    @PostMapping("/login/email")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val user = authService.login(request)
        return ResponseEntity.ok().body(user)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshRequest): ResponseEntity<LoginResponse> {
        val refresh = authService.refreshToken(request.refreshToken)
        return ResponseEntity.ok().body(refresh)
    }
}

