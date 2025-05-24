package org.scaleadvisor.backend.global.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object IdUtil {
    private val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSS")

    @JvmStatic
    fun generateId(): Long =
        LocalDateTime.now(ZoneId.systemDefault()).format(formatter).toLong()

}
