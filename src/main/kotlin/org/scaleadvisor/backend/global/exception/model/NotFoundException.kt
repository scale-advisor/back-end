package org.scaleadvisor.backend.global.exception.model

import org.scaleadvisor.backend.global.exception.BaseErrorCode

class NotFoundException(
    message: String,
    errorCode: BaseErrorCode = BaseErrorCode.NOT_FOUND_EXCEPTION
) : CustomException(message, errorCode)

