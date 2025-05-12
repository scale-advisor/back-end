package org.scaleadvisor.backend.global.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import lombok.extern.slf4j.Slf4j
import org.scaleadvisor.backend.user.domain.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.Date

@Component
@Slf4j
class JwtProvider {
    companion object {
        private val log: org.slf4j.Logger? = LoggerFactory.getLogger(JwtProvider::class.java)
    }

    @Value("\${jwt.access-token-ttl}")
    lateinit var ACCESS_TOKEN_VALID_MILLISECOND: String

    @Value("\${jwt.refresh-token-ttl}")
    lateinit var REFRESH_TOKEN_VALID_MILLISECOND: String

    @Value("\${key.salt}")
    private lateinit var salt: String
    private lateinit var secretKey: Key

    @PostConstruct
    protected fun init() {
        secretKey = Keys.hmacShaKeyFor(salt.toByteArray(StandardCharsets.UTF_8))
    }

    fun getSecretKey(): Key = secretKey

    fun createAccessToken(userId: Long, email: String): String {
        val now = Date()
        val claims: Claims = Jwts.claims().apply {
            put("userId", userId)
            put("email", email)
            put("loginType", User.LoginType.BASIC)
        }
        val token = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(System.currentTimeMillis() + ACCESS_TOKEN_VALID_MILLISECOND.toLong()))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
        log?.info("[createAccessToken] access 토큰 생성 완료: {}", token)
        return token
    }

    fun createRefreshToken(userId: Long): String {
        val now = Date()
        val claims: Claims = Jwts.claims().apply {
            put("userId", userId)
        }
        val token = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_MILLISECOND.toLong()))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
        log?.info("[RefreshAccessToken] refresh 토큰 생성 완료: {}", token)
        return token
    }

    fun parseClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
}
