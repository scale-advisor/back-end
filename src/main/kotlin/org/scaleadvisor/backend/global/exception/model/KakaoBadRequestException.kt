package org.scaleadvisor.backend.global.exception.model

import org.scaleadvisor.backend.global.exception.BaseErrorCode

class KakaoBadRequestException (
    message: String,
    errorCode: BaseErrorCode = BaseErrorCode.KAKAO_BAD_REQUEST_EXCEPTION
) : CustomException(message, errorCode)