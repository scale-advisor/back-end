package org.scaleadvisor.backend.project.application.port.usecase

import org.scaleadvisor.backend.project.domain.Project

fun interface CreateProjectUseCase {
    class CreateProjectCommand(
        val name: String,
        val description: String?
    )

    fun execute(command: CreateProjectCommand): Project

}