package org.scaleadvisor.backend.project.domain

import ProjectVersionId
import org.scaleadvisor.backend.project.domain.id.ProjectId
import java.io.Serializable

data class ProjectVersion private constructor(
    val id: ProjectVersionId                                    // ← 식별자 VO
) : Serializable {

    val projectId: ProjectId get() = id.projectId
    val major: Int        get() = id.major
    val minor: Int        get() = id.minor
    val versionNumber: String get() = "${id.major}.${id.minor}"

    fun nextMajor() = of(projectId, major + 1, 0)
    fun nextMinor() = of(projectId, major,     minor + 1)

    companion object {
        fun init(projectId: ProjectId)        = of(projectId, 1, 0)

        fun of(projectId: ProjectId, major: Int, minor: Int) =
            ProjectVersion(ProjectVersionId.of(projectId, major, minor))

        fun of(projectId: ProjectId, version: String): ProjectVersion {
            val m = Regex("""(\d+)\.(\d+)""").matchEntire(version)
                ?: throw IllegalArgumentException("Version must be like 2.3")
            val (maj, min) = m.destructured
            return of(projectId, maj.toInt(), min.toInt())
        }

        fun from(id: ProjectVersionId) = ProjectVersion(id)
    }
}