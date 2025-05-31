package org.scaleadvisor.backend.global.jwt

import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import lombok.extern.slf4j.Slf4j
import org.scaleadvisor.backend.global.exception.model.JwtExpiredException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

@Component
@Slf4j
class JwtUtil(
    private val jwtProvider: JwtProvider
) {
    companion object {
        private val log: org.slf4j.Logger? = LoggerFactory.getLogger(JwtUtil::class.java)
    }

    fun getTokenFromRequest(request: HttpServletRequest): String {
        val accessToken = request.getHeader("Authorization")
        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")) {
            log?.info("Request 상의 accessToken: {}", accessToken)
            return accessToken
        } else {
            throw JwtExpiredException("인증 토큰이 만료되었거나 잘못되었습니다.")
        }
    }

    fun getEmailFromToken(token: String?): String {
        log?.info("[getUserEmailFromToken] 토큰 기반 회원 Email 추출")
        return Jwts.parserBuilder().setSigningKey(jwtProvider.getSecretKey()).build()
            .parseClaimsJws(token).getBody().get("email").toString()
    }
}