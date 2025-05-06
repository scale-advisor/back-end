package org.scaleadvisor.backend.global.exception.model

import org.scaleadvisor.backend.global.exception.BaseErrorCode

class ForbiddenException(
    message: String,
    errorCode: BaseErrorCode = BaseErrorCode.FORBIDDEN_EXCEPTION
) : CustomException(message, errorCode)
