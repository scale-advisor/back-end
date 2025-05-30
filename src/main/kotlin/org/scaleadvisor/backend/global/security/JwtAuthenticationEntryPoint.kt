package org.scaleadvisor.backend.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.scaleadvisor.backend.global.exception.BaseErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
): AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.characterEncoding = "UTF-8"
        response.contentType = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"
        response.status = HttpStatus.FORBIDDEN.value()

        val body = mapOf(
            "errorCode"     to BaseErrorCode.JWT_EXPIRED_EXCEPTION.code,
            "message"       to BaseErrorCode.JWT_EXPIRED_EXCEPTION.message,
            "detailMessage" to "인증 토큰이 만료되었거나 잘못되었습니다."
        )

        response.status = HttpStatus.FORBIDDEN.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(objectMapper.writeValueAsString(body))
    }
}