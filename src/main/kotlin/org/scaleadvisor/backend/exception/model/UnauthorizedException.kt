package org.scaleadvisor.backend.exception.model

import org.scaleadvisor.backend.exception.BaseErrorCode

class UnauthorizedException(
    message: String,
    errorCode: BaseErrorCode = BaseErrorCode.UNAUTHORIZED_EXCEPTION
) : CustomException(message, errorCode)
