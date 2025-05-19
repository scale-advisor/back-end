package org.scaleadvisor.backend.global.exception.model

import org.scaleadvisor.backend.global.exception.BaseErrorCode

class EmailTokenGoneException(
    message: String,
    errorCode: BaseErrorCode = BaseErrorCode.EMAIL_TOKEN_GONE_EXCEPTION
) : CustomException(message, errorCode)