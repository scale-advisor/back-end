package org.scaleadvisor.backend.global.exception.model

import org.scaleadvisor.backend.global.exception.BaseErrorCode

class JwtExpiredException(
    message: String,
    errorCode: BaseErrorCode = BaseErrorCode.JWT_EXPIRED_EXCEPTION
) : CustomException(message, errorCode)
