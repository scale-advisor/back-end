package org.scaleadvisor.backend.global.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.locks.LockSupport

object IdUtil {
    private val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSS")

    @JvmStatic
    fun generateId(): Long {
//        TODO: ID 중복에 대한 임시 대처, 향후 수정 필요
        LockSupport.parkNanos(10_000)
        return LocalDateTime.now(ZoneId.systemDefault()).format(formatter).toLong()
    }

}
