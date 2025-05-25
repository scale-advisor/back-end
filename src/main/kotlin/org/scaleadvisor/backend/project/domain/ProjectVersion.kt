package org.scaleadvisor.backend.project.domain

import java.io.Serializable

data class ProjectVersion private constructor(
    var major: Int,
    var minor: Int,
) : Comparable<ProjectVersion>, Serializable {

    override fun toString(): String = "$major.$minor"

    override fun compareTo(other: ProjectVersion): Int =
        if (major != other.major) major - other.major else minor - other.minor

    fun nextMajor(): ProjectVersion = ProjectVersion(major + 1, 0)
    fun nextMinor(): ProjectVersion = ProjectVersion(major, minor + 1)

    companion object {
        @JvmField
        val INITIAL_VERSION: ProjectVersion = ProjectVersion(1, 0)

        @JvmStatic
        fun of(major: Int, minor: Int): ProjectVersion =
            ProjectVersion(major, minor)

        @JvmStatic
        fun of(version: String): ProjectVersion {
            val regex = """(\d+)\.(\d+)""".toRegex()
            val m = regex.matchEntire(version)
                ?: throw IllegalArgumentException("versionNumber must be like 2.3")

            val (maj, min) = m.destructured
            return ProjectVersion(maj.toInt(), min.toInt())
        }
    }
}