package org.scaleadvisor.backend.exception.dto

import org.scaleadvisor.backend.exception.model.CustomException

data class ErrorResponse(
    val errorCode: String,
    val message: String,
    val detailMessage: String
) {
    constructor(ex: CustomException) : this(
        errorCode = ex.errorCode!!.code,
        message = ex.errorCode.message,
        detailMessage = ex.message ?: ""
    )

    constructor(ex: CustomException, detailMessage: String) : this(
        errorCode = ex.errorCode!!.code,
        message = ex.errorCode.message,
        detailMessage = detailMessage
    )
}

