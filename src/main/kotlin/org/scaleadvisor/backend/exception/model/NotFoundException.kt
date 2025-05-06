package org.scaleadvisor.backend.exception.model

import org.scaleadvisor.backend.exception.BaseErrorCode

class NotFoundException(
    message: String,
    errorCode: BaseErrorCode = BaseErrorCode.NOT_FOUND_EXCEPTION
) : CustomException(message, errorCode)

