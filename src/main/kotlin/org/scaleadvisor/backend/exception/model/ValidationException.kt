package org.scaleadvisor.backend.exception.model

import org.scaleadvisor.backend.exception.BaseErrorCode

class ValidationException(
    message: String,
    errorCode: BaseErrorCode = BaseErrorCode.VALIDATION_EXCEPTION
) : CustomException(message, errorCode)
