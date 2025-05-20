package org.scaleadvisor.backend.project.application.port.repository.project

import org.scaleadvisor.backend.project.domain.Project

fun interface CreateProjectPort {

    fun create(project: Project)

}