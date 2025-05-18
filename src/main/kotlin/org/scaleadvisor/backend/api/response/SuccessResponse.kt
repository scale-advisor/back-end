package org.scaleadvisor.backend.api.response

import java.time.OffsetDateTime

class SuccessResponse<T>(
    val responseData: T,
    val timeStamp: OffsetDateTime = OffsetDateTime.now()
) {
    companion object {
        @JvmStatic
        fun <T> from(data: T): SuccessResponse<T> =
            SuccessResponse(data)
    }
}