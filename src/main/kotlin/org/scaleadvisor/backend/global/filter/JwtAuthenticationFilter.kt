package org.scaleadvisor.backend.global.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.scaleadvisor.backend.global.exception.model.InvalidTokenException
import org.scaleadvisor.backend.global.jwt.JwtUtil
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {
    private val pathMatcher = AntPathMatcher()

    companion object {
        private val log: org.slf4j.Logger? = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)
    }

    private val excludedPatterns = listOf(
        "/auth/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**",
        "/api-docs/**"
    )

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.servletPath
        return excludedPatterns.any { pattern -> pathMatcher.match(pattern, path) }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val accessToken = jwtUtil.getTokenFromRequest(request)
        if (accessToken.startsWith("Bearer ")) {
            val token = accessToken.substring(7)
            try {
                val userDetails = userDetailsService.loadUserByUsername(jwtUtil.getEmailFromToken(token))
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                log?.info(String.format("doFilterInternal 통과 완료"))
                SecurityContextHolder.getContext().authentication = authentication
            } catch (ex: InvalidTokenException) {
                log?.warn("doFilterInternal 실패")
                SecurityContextHolder.clearContext()
            }
        }
        filterChain.doFilter(request, response)
    }
}
