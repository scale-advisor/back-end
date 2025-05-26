package org.scaleadvisor.backend.global.oauth.kakao

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.scaleadvisor.backend.global.auth.dto.LoginResponse
import org.scaleadvisor.backend.global.auth.service.AuthService
import org.scaleadvisor.backend.global.oauth.kakao.component.KakaoCallbackService
import org.scaleadvisor.backend.global.oauth.kakao.dto.KakaoAuthorizeResponse
import org.scaleadvisor.backend.global.oauth.kakao.dto.KakaoCallbackRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Auth - Kakao")
@RestController
@RequestMapping("/auth")
class KakaoController(
    private val authService: AuthService,
    private val kakaoCallbackService: KakaoCallbackService
) {
    @GetMapping("/kakao/authorize")
    fun kakaoLoginPage(): ResponseEntity<KakaoAuthorizeResponse>{
        val kakaoAuthUrl = kakaoCallbackService.getKakaoAuthorizeUrl()
        return ResponseEntity.ok().body(KakaoAuthorizeResponse(kakaoAuthUrl))
    }

    @PostMapping("/kakao/login")
    fun kakaoLogin(@RequestBody request: KakaoCallbackRequest, response: HttpServletResponse)
            : ResponseEntity<LoginResponse> {
        val result= authService.kakaoLogin(request.code, response)
        return ResponseEntity.ok().body(result)
    }
}