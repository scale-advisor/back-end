package org.scaleadvisor.backend.project.domain.vo

import java.io.Serializable

data class VersionNumber private constructor(
    var major: Int,
    var minor: Int,
) : Comparable<VersionNumber>, Serializable {

    override fun toString(): String = "$major.$minor"

    override fun compareTo(other: VersionNumber): Int =
        if (major != other.major) major - other.major else minor - other.minor

    fun nextMajor(): VersionNumber = VersionNumber(major + 1, 0)
    fun nextMinor(): VersionNumber = VersionNumber(major, minor + 1)

    companion object {
        @JvmField
        val INITIAL_NUMBER: VersionNumber = VersionNumber(1, 0)

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