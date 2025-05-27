package org.scaleadvisor.backend.project.controller.response.project

import org.scaleadvisor.backend.project.domain.Project
import org.scaleadvisor.backend.project.domain.ProjectVersion
import java.time.LocalDateTime

data class GetProjectResponse(
    val id: String,
    val name: String,
    val description: String?,
    val updatedAt: LocalDateTime,
    val versionList: List<String>
) {

    companion object {
        @JvmStatic
        fun of(project: Project, projectVersionList: List<ProjectVersion>): GetProjectResponse =
            GetProjectResponse(
                id = project.id.toString(),
                name = project.name,
                description = project.description,
                updatedAt = project.updatedAt!!,
                versionList = projectVersionList.map { it.versionNumber }
            )
    }
}

