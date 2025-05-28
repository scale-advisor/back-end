package org.scaleadvisor.backend.global.auth.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.scaleadvisor.backend.global.auth.dto.LoginRequest
import org.scaleadvisor.backend.global.auth.dto.LoginResponse
import org.scaleadvisor.backend.global.auth.dto.SignUpRequest
import org.scaleadvisor.backend.global.auth.service.AuthService
import org.scaleadvisor.backend.global.exception.model.UnauthorizedException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Auth", description = "이메일 회원가입/로그인 관련 API")
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
    fun logout(@CookieValue(value = "refreshToken", required = false) refreshToken: String?, response: HttpServletResponse): ResponseEntity<Void> {
        val token = refreshToken
            ?: throw UnauthorizedException("refreshToken 쿠키가 없습니다. 재로그인하세요.")
        authService.logout(token, response)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/refresh")
    fun refresh(@CookieValue(value = "refreshToken", required = false) refreshToken: String?, response: HttpServletResponse
    ): ResponseEntity<LoginResponse> {
        val token = refreshToken
            ?: throw UnauthorizedException("refreshToken 쿠키가 없습니다. 재로그인하세요.")
        val refreshed = authService.refreshToken(token, response)
        return ResponseEntity.ok().body(refreshed)
    }
}

