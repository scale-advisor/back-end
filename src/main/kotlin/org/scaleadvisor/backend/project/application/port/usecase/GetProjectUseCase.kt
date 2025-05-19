package org.scaleadvisor.backend.project.application.port.usecase

import org.scaleadvisor.backend.project.domain.Project

fun interface GetProjectUseCase {

    fun findAll(userId: Long): List<Project>

}