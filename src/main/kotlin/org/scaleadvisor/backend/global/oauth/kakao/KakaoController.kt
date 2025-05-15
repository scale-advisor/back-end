package org.scaleadvisor.backend.global.oauth.kakao

import jakarta.servlet.http.HttpServletResponse
import org.scaleadvisor.backend.global.auth.dto.LoginResponse
import org.scaleadvisor.backend.global.auth.service.AuthService
import org.scaleadvisor.backend.global.oauth.kakao.component.KakaoCallbackService
import org.scaleadvisor.backend.global.oauth.kakao.dto.KakaoCallbackRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class KakaoController(
    private val authService: AuthService,
    private val kakaoCallbackService: KakaoCallbackService
) {
    @GetMapping("/kakao/authorize")
    fun kakaoLoginPage(response: HttpServletResponse){
        val kakaoAuthUrl = kakaoCallbackService.getKakaoAuthorizeUrl()
        response.sendRedirect(kakaoAuthUrl)
    }

    @RequestMapping("/kakao/callback", method = [RequestMethod.GET, RequestMethod.POST])
    fun kakaoLogin(@RequestParam code: String, response: HttpServletResponse)
            : ResponseEntity<LoginResponse> {
        val result= authService.kakaoLogin(KakaoCallbackRequest(code), response)
        return ResponseEntity.ok().body(result)
    }
}