package org.scaleadvisor.backend.project.controller.response.project

import org.scaleadvisor.backend.project.domain.Project
import java.time.LocalDateTime

data class CreateProjectResponse(
    val id: String,
    val name: String,
    val description: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        @JvmStatic
        fun from(project: Project): CreateProjectResponse {
            return CreateProjectResponse(
                id = project.id.toString(),
                name = project.name,
                description = project.description,
                createdAt = project.createdAt,
                updatedAt = project.updatedAt ?: project.createdAt
            )
        }
    }
}