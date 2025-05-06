package org.scaleadvisor.backend.global.exception.model

import org.scaleadvisor.backend.global.exception.BaseErrorCode

class InternalServerException(
    message: String,
    errorCode: BaseErrorCode = BaseErrorCode.INTERNAL_SERVER_EXCEPTION
) : CustomException(message, errorCode)
