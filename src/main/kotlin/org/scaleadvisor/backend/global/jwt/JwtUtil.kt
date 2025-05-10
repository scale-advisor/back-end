package org.scaleadvisor.backend.global.jwt

import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import lombok.extern.slf4j.Slf4j
import org.scaleadvisor.backend.global.exception.constant.TokenMessageConstant
import org.scaleadvisor.backend.global.exception.model.InvalidTokenException
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
            throw InvalidTokenException(TokenMessageConstant.INVALID_TOKEN_MESSAGE)
        }
    }

    fun getUserIdFromToken(token: String?): Long {
        log?.info("[getUserIdFromToken] 토큰 기반 회원 ID 추출")
        return Jwts.parserBuilder().setSigningKey(jwtProvider.getSecretKey()).build()
            .parseClaimsJws(token).getBody().get("userId").toString().toLong()
    }

    fun getEmailFromToken(token: String?): String {
        log?.info("[getUserEmailFromToken] 토큰 기반 회원 Email 추출")
        return Jwts.parserBuilder().setSigningKey(jwtProvider.getSecretKey()).build()
            .parseClaimsJws(token).getBody().get("email").toString()
    }
}