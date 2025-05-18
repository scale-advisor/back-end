package org.scaleadvisor.backend.project.application.port.repository

import org.scaleadvisor.backend.project.domain.Project

fun interface CreateProjectRepository {

    fun createProject(project: Project): Project

}