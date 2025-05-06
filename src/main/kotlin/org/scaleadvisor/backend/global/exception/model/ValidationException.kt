package org.scaleadvisor.backend.global.exception.model

import org.scaleadvisor.backend.global.exception.BaseErrorCode

class ValidationException(
    message: String,
    errorCode: BaseErrorCode = BaseErrorCode.VALIDATION_EXCEPTION
) : CustomException(message, errorCode)
