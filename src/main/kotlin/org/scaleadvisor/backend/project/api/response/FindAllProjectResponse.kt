package org.scaleadvisor.backend.project.api.response

import org.scaleadvisor.backend.project.domain.Project
import java.io.Serializable
import java.time.LocalDateTime

data class FindAllProjectResponse(
    val projects: List<ProjectDTO>
) : Serializable {

    data class ProjectDTO(
        val id: String,
        val name: String,
        val description: String?,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
    ) {
        companion object {
            fun from(project: Project): ProjectDTO = ProjectDTO(
                id = project.id.toString(),
                name = project.name,
                description = project.description,
                createdAt = project.createdAt,
                updatedAt = project.updatedAt!!
            )
        }
    }

    companion object {
        fun from(projects: List<Project>): FindAllProjectResponse =
            FindAllProjectResponse(
                projects.map { ProjectDTO.from(it) }
            )
    }
}