package org.scaleadvisor.backend.global.exception.model

import org.scaleadvisor.backend.global.exception.BaseErrorCode

class InvalidTokenException (
    message: String,
    errorCode: BaseErrorCode = BaseErrorCode.INVALID_TOKEN_EXCEPTION
) : CustomException(message, errorCode)
