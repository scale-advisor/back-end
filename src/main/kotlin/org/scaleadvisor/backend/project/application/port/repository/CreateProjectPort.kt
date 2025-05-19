package org.scaleadvisor.backend.project.application.port.repository

import org.scaleadvisor.backend.project.domain.Project

fun interface CreateProjectPort {

    fun create(project: Project)

}