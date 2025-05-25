package org.scaleadvisor.backend.project.domain

import java.io.Serializable

/**
 * ``major.minor`` 형식을 캡슐화한 불변 값 타입.
 *
 *  - 자바에서 바로 `VersionNumber.of("1.2")`, `VersionNumber.of(1,2)` 로 생성 가능
 *  - toString() 은 항상 "major.minor"
 *  - Comparable 구현으로 정렬 가능 (1.10 > 1.2)
 */
data class VersionNumber private constructor(
    val major: Int,
    val minor: Int,
) : Comparable<VersionNumber>, Serializable {

    override fun toString(): String = "$major.$minor"

    override fun compareTo(other: VersionNumber): Int =
        if (major != other.major) major - other.major else minor - other.minor

    companion object {
        @JvmField
        val INITIAL: VersionNumber = VersionNumber(1, 0)

        @JvmStatic
        fun of(major: Int, minor: Int): VersionNumber =
            VersionNumber(major, minor)

        @JvmStatic
        fun of(text: String): VersionNumber {
            val regex = """(\d+)\.(\d+)""".toRegex()
            val m = regex.matchEntire(text)
                ?: throw IllegalArgumentException("versionNumber must be like 2.3")

            val (maj, min) = m.destructured
            return VersionNumber(maj.toInt(), min.toInt())
        }
    }
}