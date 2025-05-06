package org.scaleadvisor.backend.global.exception.model

import lombok.Getter
import org.scaleadvisor.backend.global.exception.BaseErrorCode

@Getter
abstract class CustomException : RuntimeException {
    val errorCode: BaseErrorCode?

    constructor(message: String, errorCode: BaseErrorCode) : super(message) {
        this.errorCode = errorCode
    }

    constructor(message: String) : super(message) {
        this.errorCode = null
    }
}