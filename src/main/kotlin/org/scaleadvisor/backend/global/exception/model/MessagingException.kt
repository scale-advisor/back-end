package org.scaleadvisor.backend.global.exception.model

import org.scaleadvisor.backend.global.exception.BaseErrorCode

class MessagingException (
    message: String,
    errorCode: BaseErrorCode = BaseErrorCode.MESSAGING_EXCEPTION
) : CustomException(message, errorCode)