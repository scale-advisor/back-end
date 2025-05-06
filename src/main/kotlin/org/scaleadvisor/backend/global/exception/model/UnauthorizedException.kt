package org.scaleadvisor.backend.global.exception.model

import org.scaleadvisor.backend.global.exception.BaseErrorCode

class UnauthorizedException(
    message: String,
    errorCode: BaseErrorCode = BaseErrorCode.UNAUTHORIZED_EXCEPTION
) : CustomException(message, errorCode)
