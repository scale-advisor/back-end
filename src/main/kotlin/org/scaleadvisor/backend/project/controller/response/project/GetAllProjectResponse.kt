package org.scaleadvisor.backend.project.controller.response.project

import org.scaleadvisor.backend.project.domain.Project
import org.scaleadvisor.backend.project.domain.VersionNumber
import java.time.LocalDateTime

data class GetAllProjectResponse(
    val projects: List<ProjectDTO>
) {

    data class ProjectDTO(
        val id: String,
        val name: String,
        val description: String?,
        val updatedAt: LocalDateTime,
        val versionList: List<String>
    ) {
        companion object {
            @JvmStatic
            fun of(project: Project, versionList: List<VersionNumber>): ProjectDTO = ProjectDTO(
                id = project.id.toString(),
                name = project.name,
                description = project.description,
                updatedAt = project.updatedAt!!,
                versionList = versionList.map { it.toString() }
            )
        }
    }

    companion object {
        @JvmStatic
        fun from(projects: List<ProjectDTO>): GetAllProjectResponse =
            GetAllProjectResponse(projects)
    }
}