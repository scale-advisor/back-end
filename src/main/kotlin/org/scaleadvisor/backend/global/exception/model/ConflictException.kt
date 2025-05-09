package org.scaleadvisor.backend.global.exception.model

import org.scaleadvisor.backend.global.exception.BaseErrorCode

class ConflictException (
    message: String,
    errorCode: BaseErrorCode = BaseErrorCode.CONFLICT_EXCEPTION
) : CustomException(message, errorCode)
