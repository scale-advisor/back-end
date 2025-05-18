package org.scaleadvisor.backend.project.application.port.usecase

import org.scaleadvisor.backend.project.api.response.FindAllProjectResponse.ProjectDTO

fun interface GetProjectUseCase {

    fun findAll(userId: Long): List<ProjectDTO>

}