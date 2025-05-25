package org.scaleadvisor.backend.global.security

import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.global.exception.model.UnauthorizedException
import org.springframework.security.core.context.SecurityContextHolder

object CurrentUserIdExtractor {

    @JvmStatic
    fun getCurrentUserIdFromSecurity(): Long?{
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw UnauthorizedException("만료 되거나 잘못된 인증 입니다.")

        val principal = authentication.principal
        if (principal !is CustomUserDetails) {
            throw ForbiddenException("허용되지 않은 접근입니다.")
        }
        return principal.getUserEntity().userId
    }
}