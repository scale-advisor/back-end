package org.scaleadvisor.backend.global.auth.controller

import jakarta.servlet.http.HttpServletResponse
import org.scaleadvisor.backend.global.auth.dto.*
import org.scaleadvisor.backend.global.auth.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<String> {
        val email = authService.signup(request)
        return ResponseEntity.ok().body(email)
    }

    @PostMapping("/login/email")
    fun login(@RequestBody request: LoginRequest, response: HttpServletResponse): ResponseEntity<LoginResponse> {
        val result = authService.login(request, response)
        return ResponseEntity.ok().body(result)
    }

    @PostMapping("/logout")
    fun logout(@CookieValue("refreshToken") refreshToken: String, response: HttpServletResponse): ResponseEntity<Void> {
        authService.logout(refreshToken, response)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/refresh")
    fun refresh(@CookieValue("refreshToken") refreshToken: String, response: HttpServletResponse
    ): ResponseEntity<LoginResponse> {
        val refreshed = authService.refreshToken(refreshToken, response)
        return ResponseEntity.ok().body(refreshed)
    }
}

