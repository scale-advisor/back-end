package org.scaleadvisor.backend.exception.model

import org.scaleadvisor.backend.exception.BaseErrorCode

class ForbiddenException(
    message: String,
    errorCode: BaseErrorCode = BaseErrorCode.FORBIDDEN_EXCEPTION
) : CustomException(message, errorCode)
