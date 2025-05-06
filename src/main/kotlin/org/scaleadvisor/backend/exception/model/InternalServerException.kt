package org.scaleadvisor.backend.exception.model

import org.scaleadvisor.backend.exception.BaseErrorCode

class InternalServerException(
    message: String,
    errorCode: BaseErrorCode = BaseErrorCode.INTERNAL_SERVER_EXCEPTION
) : CustomException(message, errorCode)
