package org.scaleadvisor.backend.global.security

import org.scaleadvisor.backend.global.exception.model.ForbiddenException
import org.scaleadvisor.backend.global.exception.model.InvalidTokenException
import org.springframework.security.core.context.SecurityContextHolder

class CurrentUserIdExtractor {

    companion object{
        @JvmStatic
        fun getCurrentUserIdFromSecurity(): Long?{
            val authentication = SecurityContextHolder.getContext().authentication
                ?: throw InvalidTokenException("만료 되거나 잘못된 토큰 입니다.")

            val principal = authentication.principal
            if (principal !is CustomUserDetails) {
                throw ForbiddenException("허용되지 않은 접근입니다.")
            }

            return principal.getUserEntity().userId

        }
    }
}