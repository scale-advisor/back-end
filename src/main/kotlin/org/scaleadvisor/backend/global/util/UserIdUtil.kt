package org.scaleadvisor.backend.global.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object IdUtil {
    private val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS")

    @JvmStatic
    fun generateId(): Long =
        LocalDateTime.now()
            .format(formatter)
            .toLong()
}