package org.scaleadvisor.backend.project.application.port.usecase

import org.scaleadvisor.backend.project.domain.Project

fun interface CreateProjectUseCase {
    class CreateProjectCommand(
        val userId: Long,
        val name: String,
        val description: String?,
    )

    fun create(command: CreateProjectCommand): Project

}