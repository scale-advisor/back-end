package org.scaleadvisor.backend.global.oauth.kakao.service

import org.scaleadvisor.backend.global.oauth.kakao.component.KakaoCallbackService
import org.scaleadvisor.backend.global.oauth.kakao.dto.KakaoCallbackRequest
import org.scaleadvisor.backend.user.domain.User
import org.scaleadvisor.backend.user.dto.LoginRequest
import org.scaleadvisor.backend.user.dto.LoginResponse
import org.scaleadvisor.backend.user.repository.UserRepository
import org.scaleadvisor.backend.user.service.AuthService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class KakaoService(
    private val kakaoCallbackService: KakaoCallbackService,
    private val passwordEncoder: PasswordEncoder,
    private val authService: AuthService,
    private val userRepository: UserRepository
) {

    fun login(request: KakaoCallbackRequest): LoginResponse {
        val kakaoAccessToken = kakaoCallbackService.getKakaoToken(request.code)
        val userInfo = kakaoCallbackService.getUserInfoFromKakaoToken(kakaoAccessToken)

        val kakaoUserId  = userInfo["id"].toString()
        val nickname = userInfo["nickname"].toString()
        val email    = userInfo["email"].toString()

        if (!userRepository.existsByEmail(email)) {
            // 이 부분 같은 경우 다시 의논
            val encodedPassword = passwordEncoder.encode(kakaoUserId)
            val newUser = User.of(
                email     = email,
                password  = encodedPassword,
                name      = nickname,
                loginType = User.LoginType.KAKAO,
                socialId  = kakaoUserId
            )
            userRepository.createUser(newUser)
        }

        return authService.login(
            LoginRequest(
                email    = email,
                password = kakaoUserId
            )
        )
    }
}