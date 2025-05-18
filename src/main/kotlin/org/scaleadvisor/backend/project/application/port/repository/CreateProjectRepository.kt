package org.scaleadvisor.backend.project.application.port.repository

import org.scaleadvisor.backend.project.domain.Project

interface CreateProjectRepository {

    fun create(project: Project): Project

}